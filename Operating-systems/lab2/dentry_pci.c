#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <linux/proc_fs.h>
#include <linux/fs.h>
#include <linux/seq_file.h>
#include <linux/pci.h>
#include <linux/mutex.h>
#include <linux/dcache.h>
#include <linux/namei.h>

#define PCI_PROCFS_FILE_NAME "pciinfo"
#define DENTRY_PROCFS_FILE_NAME "dentryinfo"

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Dmitry");
MODULE_DESCRIPTION("lspci and dentry module");

struct mutex m;
struct proc_dir_entry *proc_pci_file;
char *path;

enum {
    // Unclassified device
    NON_VGA_UNCLASSIFIED_DEVICE = 0x000,
    VGA_COMPATIBLE_UNCLASSIFIED_DEVICE = 0x001,
    IMAGE_COPROCESSOR = 0x005,

    // Mass storage controller
    SCSI_STORAGE_CONTROLLER = 0x100,
    IDE_INTERFACE = 0x101,
    FLOPPY_DISK_CONTROLLER = 0x102,
    IPI_BUS_CONTROLLER = 0x103,
    RAID_BUS_CONTROLLER = 0x104,
    ATA_CONTROLLER = 0x105,
    SATA_CONTROLLER = 0x106,
    SERIAL_ATTACHED_SCSI_CONTROLLER = 0x107,
    NON_VOLATILE_MEMORY_CONTROLLER = 0x108,
    UNIVERSAL_FLASH_STORAGE_CONTROLLER = 0x109,
    MASS_STORAGE_CONTROLLER = 0x180,

    // Network controller
    ETHERNET_CONTROLLER = 0x200,
    TOKEN_RING_NETWORK_CONTROLLER = 0x201,
    FDDI_NETWORK_CONTROLLER = 0x202,
    ATM_NETWORK_CONTROLLER = 0x203,
    ISDN_CONTROLLER = 0x204,
    WORLD_FIP_CONTROLLER = 0x205,
    PICMG_CONTROLLER = 0x206,
    INFINIBAND_CONTROLLER = 0x207,
    FABRIC_CONTROLLER = 0x208,
    NETWORK_CONTROLLER = 0x280,

    // Display controller
    VGA_COMPATIBLE_CONTROLLER = 0x300,
    XGA_COMPATIBLE_CONTROLLER = 0x301,
    D3_CONTROLLER = 0x302,
    DISPLAY_CONTROLLER = 0x380,

    // Multimedia controller
    MULTIMEDIA_VIDEO_CONTROLLER = 0x400,
    MULTIMEDIA_AUDIO_CONTROLLER = 0x401,
    COMPUTER_TELEPHONY_DEVICE = 0x402,
    AUDIO_DEVICE = 0x403,
    MULTIMEDIA_CONTROLLER = 0x480,

    // Memory controller
    RAM_MEMORY = 0x500,
    FLASH_MEMORY = 0x501,
    CXL = 0x502,
    MEMORY_CONTROLLER = 0x580,

    // Bridge controller
    HOST_BRIDGE = 0x600,
    ISA_BRIDGE = 0x601,
    EISA_BRIDGE = 0x602,
    MICROCHANNEL_BRIDGE = 0x603,
    PCI_BRIDGE = 0x604,
    PCMCIA_BRIDGE = 0x605,
    NUBUS_BRIDGE = 0x606,
    CARDBUS_BRIDGE = 0x607,
    RACEWAY_BRIDGE = 0x608,
    SEMITRANSPARENT_PCI_TO_PCI_BRIDGE = 0x609,
    INFINIBAND_TO_PCI_BRIDGE = 0x60a,
    BRIDGE = 0x680,

    // Communication controller
    SERIAL_CONTROLLER = 0x700,
    PARALLEL_CONTROLLER = 0x701,
    MULTIPORT_SERIAL_CONTROLLER = 0x702,
    MODERN_CONTROLLER = 0x703,
    GPIB_CONTROLLER = 0x704,
    SMARD_CARD_CONTROLLER = 0x705,
    COMMUNICATION_CONTROLLER = 0x780,

    // Generic system peripheral
    PIC = 0x800,
    DMA_CONTROLLER = 0x801,
    TIMER = 0x802,
    RTC = 0x803,
    PCI_HOST_PUG_CONTROLLER = 0x804,
    SD_HOST_CONTROLLER = 0x805,
    IOMMU = 0x806,
    SYSTEM_PIRIPHERAL = 0x880,
    TIMING_CARD = 0x899,

    // Input device controller
    KEYBOARD_CONTROLLER = 0x900,
    DIGITIZER_PEN = 0x901,
    MOUSE_CONTROLLER = 0x902,
    SCANNER_CONTROLLER = 0x903,
    GAMEPORT_CONTROLLER = 0x904,
    INPUT_DEVICE_CONTROLLER = 0x980,

    // Docking station
    GENERIC_DOCKING_STATION = 0xa00,
    DOCKING_STATION = 0xa01,

    // Processor
    X386 = 0xb00,
    X486 = 0xb01,
    PENTIUM = 0xb02,
    ALPHA = 0xb10,
    POWER_PC = 0xb20,
    MIPS = 0xb30,
    CO_PROCESSOR = 0xb40,

    // Serial bus controller
    FIREWIRE = 0xc00,
    ACCESS_BUS = 0xc01,
    SSA = 0xc02,
    USB_CONTROLLER = 0xc03,
    FIBRE_CHANNEL = 0xc04,
    SMBUS = 0xc05,
    INFINIBAND = 0xc06,
    IPMI_INTERFACE = 0xc07,
    SERCOS_INTERFACE = 0xc08,
    CANBUS = 0xc09,
    SERIAL_BUS_CONTROLLER = 0xc80,

    // Wireless controller
    IRDA_CONTROLLER = 0xd00,
    CONSUMER_CONTROLLER = 0xd01,
    RF_CONTROLLER = 0xd10,
    BLUETOOTH_CONTROLLER = 0xd11,
    BROADBAND_CONTROLLER = 0xd12,
    V8021A_CONTROLLER = 0xd20,
    V8021b_CONTROLLER = 0xd21,
    WIRELESS_CONTROLLER = 0xd80,

    // Intelligent controller
    I2O = 0xe00,

    // Satellite communication controller
    SATELLITE_TV_CONTROLLER = 0xf01,
    SATELLITE_AUDIO_COMMUNICATION_CONTROLLER = 0xf02,
    SATELLITE_VOICE_COMMUNICATION_CONTROLLER = 0xf03,
    SATELLITE_DATA_COMMUNICATION_CONTROLLER = 0xf04,

    // Encryption controller
    NETWORK_AND_COMPUTING_ENCRYPTION_DEVICE = 0x1000,
    ENTERTAINMENT_ENCRYPTION_DEVICE = 0x1001,
    ENCRYPTION_DEVICE = 0x1080,

    // Signal processing controller
    DPIO_MODULE = 0x1100,
    PERFOMANCE_COUNTERS = 0x1101,
    COMMUNICATION_SYNCHRONIZER = 0x1110,
    SIGNAL_PROCESSING_MANAGEMENT = 0x1120,
    SIGNAL_PROCESSING_CONTROLLER = 0x1180,

    // Processing accelerators
    PROCESSING_ACCELERATORS = 0x1200,
    SNIA_SMART_DATA_ACCELERATOR_INTERFACE = 0x1201,

    // Non-esential instrumentation
    NON_ESSENTIAL_INSTRUMENTATION = 0x1300,

    // Coprocessor
    COPROCESSOR = 0x4000,

    // Unassigned class
    UNASSIGNED_CLASS = 0xff00,
};

char *get_class(struct pci_dev *dev) 
{
    switch((dev->class >> 8 & 0xffff)) 
    {
        case NON_VGA_UNCLASSIFIED_DEVICE:
            return "  Class: Non-VGA unclassified device";  
        case VGA_COMPATIBLE_UNCLASSIFIED_DEVICE:
            return "  Class: VGA compatible unclassified device";
        case IMAGE_COPROCESSOR:
            return "  Class: Image coprocessor";
        

        case SCSI_STORAGE_CONTROLLER:
            return "  Class: SCSI storage controller";  
        case IDE_INTERFACE:
            return "  Class: IDE interface"; 
        case FLOPPY_DISK_CONTROLLER:
            return "  Class: Floppy disk controller";
        case IPI_BUS_CONTROLLER:
            return "  Class: IPI bus controller";            
        case RAID_BUS_CONTROLLER:
            return "  Class: RAID bus controller";
        case ATA_CONTROLLER:
            return "  Class: ATA controller";
        case SATA_CONTROLLER:
            return "  Class: SATA controller";
        case SERIAL_ATTACHED_SCSI_CONTROLLER:
            return "  Class: Serial Attached SCSI controller";
        case NON_VOLATILE_MEMORY_CONTROLLER:
            return "  Class: Non-Volatile memory controller";
        case UNIVERSAL_FLASH_STORAGE_CONTROLLER:
            return "  Class: Universal Flash Storage controller";
        case MASS_STORAGE_CONTROLLER:
            return "  Class: Mass storage controller";

        case ETHERNET_CONTROLLER:
            return "  Class: Ethernet controller";
        case TOKEN_RING_NETWORK_CONTROLLER:
            return "  Class: Token ring network controller";
        case FDDI_NETWORK_CONTROLLER:
            return "  Class: FDDI network controller";
        case ATM_NETWORK_CONTROLLER:
            return "  Class: ATM network controller";
        case ISDN_CONTROLLER:
            return "  Class: ISDN controller";
        case WORLD_FIP_CONTROLLER:
            return "  Class: WorldFip controller";
        case PICMG_CONTROLLER:
            return "  Class: PICMG controller";
        case INFINIBAND_CONTROLLER:
            return "  Class: Infiniband controller";
        case FABRIC_CONTROLLER:
            return "  Class: Fabric controller";
        case NETWORK_CONTROLLER:
            return "  Class: Network controller";

        case VGA_COMPATIBLE_CONTROLLER:
            return "  Class: VGA compatible controller";
        case XGA_COMPATIBLE_CONTROLLER:
            return "  Class: XGA compatible controller";
        case D3_CONTROLLER:
            return "  Class: 3D controller";
        case DISPLAY_CONTROLLER:
            return "  Class: Display controller";

        case MULTIMEDIA_VIDEO_CONTROLLER:
            return "  Class: Multimedia video controller";
        case MULTIMEDIA_AUDIO_CONTROLLER:
            return "  Class: Multimedia audio controller";
        case COMPUTER_TELEPHONY_DEVICE:
            return "  Class: Computer telephony device";
        case AUDIO_DEVICE:
            return "  Class: Audio device";
        case MULTIMEDIA_CONTROLLER:
            return "  Class: Mulimedia controller";

        case RAM_MEMORY:
            return "  Class: RAM memory";
        case FLASH_MEMORY:
            return "  Class: FLASH memory";
        case CXL:
            return "  Class: CXL";
        case MEMORY_CONTROLLER:
            return "  Class: Memory controller";

        case HOST_BRIDGE:
            return "  Class: Host bridge";
        case ISA_BRIDGE:
            return "  Class: ISA bridge";
        case EISA_BRIDGE:
            return "  Class: EISA bridge";
        case MICROCHANNEL_BRIDGE:
            return "  Class: MicroChannel bridge";
        case PCI_BRIDGE:
            return "  Class: PCI bridge";
        case PCMCIA_BRIDGE:
            return "  Class: PCMCIA bridge";
        case NUBUS_BRIDGE:
            return "  Class: NuBus bridge";
        case CARDBUS_BRIDGE:
            return "  Class: CardBus bridge";
        case RACEWAY_BRIDGE:
            return "  Class: RACEway bridge";
        case SEMITRANSPARENT_PCI_TO_PCI_BRIDGE:
            return "  Class: Semi-transparent PCI-to-PCI bridge";
        case INFINIBAND_TO_PCI_BRIDGE:
            return "  Class: InfiniBand to PCI host bridge";
        case BRIDGE:
            return "  Class: Bridge";

        case SERIAL_CONTROLLER:
            return "  Class: Serial controller";
        case PARALLEL_CONTROLLER:
            return "  Class: Parallel controller";
        case MULTIPORT_SERIAL_CONTROLLER:
            return "  Class: Multiport serial controller";
        case MODERN_CONTROLLER:
            return "  Class: Modern controller";
        case GPIB_CONTROLLER:
            return "  Class: GPIB controller";
        case SMARD_CARD_CONTROLLER:
            return "  Class: Smard Card controller";
        case COMMUNICATION_CONTROLLER:
            return "  Class: Communication controller";

        case PIC:
            return "  Class: PIC";
        case DMA_CONTROLLER:
            return "  Class: DMA controller";
        case TIMER:
            return "  Class: Time";
        case RTC:
            return "  Class: RTC";
        case PCI_HOST_PUG_CONTROLLER:
            return "  Class: PCI Hot-plug controller";
        case SD_HOST_CONTROLLER:
            return "  Class: SD Host controller";
        case IOMMU:
            return "  Class: IOMMU";
        case SYSTEM_PIRIPHERAL:
            return "  Class: System peripheral";
        case TIMING_CARD:
            return "  Class: Timing Card";

        case KEYBOARD_CONTROLLER:
            return "  Class: Keyboard controller";
        case DIGITIZER_PEN:
            return "  Class: Digitizer controller";
        case MOUSE_CONTROLLER:
            return "  Class: Mouse controller";
        case SCANNER_CONTROLLER:
            return "  Class: Scanner controller";
        case GAMEPORT_CONTROLLER:
            return "  Class: Gameport controller";
        case INPUT_DEVICE_CONTROLLER:
            return "  Class: Input device controller";

        case X386:
            return "  Class: 386";
        case X486:
            return "  Class: 486";
        case PENTIUM:
            return "  Class: Pentium";
        case ALPHA:
            return "  Class: Alpha";
        case POWER_PC:
            return "  Class: Power PC";
        case MIPS:
            return "  Class: MIPS";
        case CO_PROCESSOR:
            return "  Class: Co-processor";

        case GENERIC_DOCKING_STATION:
            return "  Class: Generic docking station";
        case DOCKING_STATION:
            return "  Class: Docking station";

        case FIREWIRE:
            return "  Class: Fire Wire (IEEE 1394)";
        case ACCESS_BUS:
            return "  Class: ACCESS Bus";
        case SSA:
            return "  Class: SSA";
        case USB_CONTROLLER:
            return "  Class: USB controller";
        case FIBRE_CHANNEL:
            return "  Class: Fibre Channel";
        case SMBUS:
            return "  Class: SMBus";
        case INFINIBAND:
            return "  Class: InfiniBand";
        case IPMI_INTERFACE:
            return "  Class: IMPI Interface";
        case SERCOS_INTERFACE:
            return "  Class: SERCIS interface";
        case CANBUS:
            return "  Class: CANBUS";
        case SERIAL_BUS_CONTROLLER:
            return "  Class: Serial bus controller";

        case IRDA_CONTROLLER:
            return "  Class:IRDA controller ";
        case CONSUMER_CONTROLLER:
            return "  Class: Consumer IR controller";
        case RF_CONTROLLER:
            return "  Class: RF controller";
        case BLUETOOTH_CONTROLLER:
            return "  Class: Bluetooth";
        case BROADBAND_CONTROLLER:
            return "  Class: Broadband";
        case V8021A_CONTROLLER:
            return "  Class: 802.1a controller";
        case V8021b_CONTROLLER:
            return "  Class: 802.1b controller";
        case WIRELESS_CONTROLLER:
            return "  Class: Wireless controller";

        case I2O:
            return "  Class: I2O";
        
        case SATELLITE_TV_CONTROLLER:
            return "  Class: Satellite TV controller";    
        case SATELLITE_AUDIO_COMMUNICATION_CONTROLLER:
            return "  Class: Satellite audio communication controller";
        case SATELLITE_VOICE_COMMUNICATION_CONTROLLER:
            return "  Class: Satellite voice communication controller";
        case SATELLITE_DATA_COMMUNICATION_CONTROLLER:
            return "  Class: Satellite data communication controller";

        case NETWORK_AND_COMPUTING_ENCRYPTION_DEVICE:
            return "  Class: Network and computing encryption device";
        case ENTERTAINMENT_ENCRYPTION_DEVICE:
            return "  Class: Entertainment encryption device";
        case ENCRYPTION_DEVICE:
            return "  Class: Encryption device";

        case DPIO_MODULE:
            return "  Class: DPIO module";
        case PERFOMANCE_COUNTERS:
            return "  Class: Perfomance counters";
        case COMMUNICATION_SYNCHRONIZER:
            return "  Class: Communication synchonizer";
        case SIGNAL_PROCESSING_MANAGEMENT:
            return "  Class: Signal processing management";
        case SIGNAL_PROCESSING_CONTROLLER:
            return "  Class: Signal processing controller";

        case PROCESSING_ACCELERATORS:
            return "  Class: Processing accelerators";
        case SNIA_SMART_DATA_ACCELERATOR_INTERFACE:
            return "  Class: SNIA Smart Data Accelerator Interface (SDXI) controller";

        case COPROCESSOR:
            return "  Class: Coprocessor";

        case NON_ESSENTIAL_INSTRUMENTATION:
            return "  Class: Non-Essential Instrumentation";

        case UNASSIGNED_CLASS:
            return "  Class: Unassigned class";
            
        default:
            return "  Class: Unknown class";
    }
}

// Will be called when somebody read /proc/pciinfo
static int procfs_pci_read(struct seq_file *file, void *v) {
    mutex_lock(&m);
    struct pci_dev *dev;

    pr_info("PCI devices:\n");

    while((dev = pci_get_device(PCI_ANY_ID, PCI_ANY_ID, dev))) {
        if (dev == NULL) {
            continue;
        }
        
        pr_info("PCI Device:\n");
        pr_info("  Name: %s\n", pci_name(dev));
        pr_info("%s", get_class(dev));
        pr_info("  Full class: %02x\n", dev->class);
        pr_info("  Device: %02x\n", dev->device);
        pr_info("  Subdevice: %02x\n", dev->subsystem_device);
        pr_info("  Vendor: %02x\n", dev->vendor);
        pr_info("  Subvendor: %02x\n", dev->subsystem_vendor);
        pr_info("  Revision: %02x\n", dev->revision);
        pr_info("\n");

        seq_printf(file, "PCI Device:\n  Name: %s\n%s\n  Full class: %02x\n  Device: %02x\n  Subdevice: %02x\n  Vendor: %02x\n  Subvendor: %02x\n  Revision: %02x\n\n", 
                         pci_name(dev), get_class(dev), dev->class, dev->device, dev->subsystem_device, dev->vendor, dev->subsystem_vendor, dev->revision);
    }

    mutex_unlock(&m);
    return 0;
}

// Will be called when somebody read /proc/dentryinfo
static int procfs_dentry_show(struct seq_file *file, void *v) {
    struct dentry *dentry;
    struct path file_path;

    if (path == NULL) {
        pr_err("path is empty");
        return -ENOMEM;
    }

    mutex_lock(&m);
    if (kern_path(path, 0, &file_path)) {
        mutex_unlock(&m);
        pr_err("Error: directory %s could not be found\n", path);
        seq_printf(file, "Error: directory %s could not be found\n", path);
        return 0;
    }

    dentry = file_path.dentry;
    if (dentry == NULL) {
        mutex_unlock(&m);
        pr_err("Error: directory %s could not be found\n", path);
        seq_printf(file, "Error: directory %s could not be found\n", path);
        return 0;
    }

    pr_info("Dentry info:\n");
    pr_info("  Name: %s\n", dentry->d_iname);
    pr_info("  Inode: %lu\n", dentry->d_inode->i_ino);
    pr_info("  Parent: %s\n", dentry->d_parent->d_iname);
    pr_info("  Permissions: %o\n", dentry->d_inode->i_mode & 0777);
    pr_info("  Link count: %d\n", dentry->d_inode->i_nlink);
    pr_info("  Size: %lld\n", dentry->d_inode->i_size);
    pr_info("  Access time: %lld\n", dentry->d_inode->i_atime.tv_sec);
    pr_info("  Modify time: %lld\n", dentry->d_inode->i_mtime.tv_sec);
    pr_info("  Change time: %lld\n", dentry->d_inode->i_ctime.tv_sec);

    seq_printf(file, "Dentry info:\n  Name: %s\n  Inode: %lu\n  Parent: %s\n  Permissions: %o\n  Link count: %d\n  Size: %lld\n  Access time: %lld\n  Modify time: %lld\n  Change time: %lld\n", 
                      dentry->d_iname, dentry->d_inode->i_ino, dentry->d_parent->d_iname, (dentry->d_inode->i_mode & 0777), dentry->d_inode->i_nlink, dentry->d_inode->i_size, dentry->d_inode->i_atime.tv_sec, dentry->d_inode->i_mtime.tv_sec, dentry->d_inode->i_ctime.tv_sec);
    
    path_put(&file_path);
    dput(dentry);
    mutex_unlock(&m);
    return 0;
}

static ssize_t procfs_dentry_write(struct file *file, const char __user *buffer, size_t count, loff_t *ppos) { 
    if (path != NULL) {
        kfree(path);
    }
    path = kmalloc(sizeof(char) * (count + 1), GFP_KERNEL); // path plus '\0'

    if (copy_from_user(path, buffer, count)) {
        kfree(path);
        return -EFAULT;
    }

    path[count] = '\0';

    pr_info("%s was written as dentry path\n", path);

    return count;
}


static int procfs_dentry_open(struct inode *inode, struct file *file) {
    return single_open(file, procfs_dentry_show, NULL);
}

static struct proc_ops dentry_fops = {
    .proc_open = procfs_dentry_open,
    .proc_read = seq_read,
    .proc_write = procfs_dentry_write,
    .proc_release = single_release,
};

static int __init my_module_init(void)
{
    proc_pci_file = proc_create_single(PCI_PROCFS_FILE_NAME, 0644, NULL, procfs_pci_read);
    proc_create(DENTRY_PROCFS_FILE_NAME, 0666, NULL, &dentry_fops);

    if (proc_pci_file == NULL) {
        pr_err("Failed to create proc entry\n");
        return -ENOMEM;
    }

    mutex_init(&m);
    pr_info("Module lspci and dentry initialized");
    return 0;
}

static void __exit my_module_exit(void)
{
    kfree(path);
    proc_remove(proc_pci_file);
    remove_proc_entry(DENTRY_PROCFS_FILE_NAME, NULL);
    mutex_destroy(&m);
    pr_info("Module lspci and dentry unloaded\n");
}

module_init(my_module_init);
module_exit(my_module_exit);
