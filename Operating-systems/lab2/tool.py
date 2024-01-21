import argparse
import json
import os
import sys
import datetime

def parse_info(file_path):
    info = []
    current_device = -1
    key, value = '', ''
    lines = []

    with open(file_path, 'r') as file:
        lines = file.readlines()

    for line in lines:
        line = line.strip()
        if line == '' or line == 'PCI Device:' or line == 'Dentry info:':
            continue
        
        if line.startswith('Error:'):
            print(line)
            exit()
        
        fields = line.split(': ')
        if len(fields) < 2:
            key, value = fields[0], ''
        else:
            key, value = fields[0], fields[1]
        
        if line.startswith('Name'):
            info.append({})
            current_device += 1
        
        info[current_device][key] = value

    return info, lines

def get_vendor(vendor):
    with open('/usr/share/misc/pci.ids', 'r') as file:
        lines = file.readlines()

    for line in lines:
        if line[0] != '\t' and vendor in line:
            return ' '.join(line.split()[1:]).strip()
    
    return ''

def get_device(device, vendor):
    current_vendor = ''
    with open('/usr/share/misc/pci.ids', 'r') as file:
        lines = file.readlines()

    for line in lines:
        if line[0] != '\t' and line != '' and '#' not in line and line != '\n':
            current_vendor = ' '.join(line.split()[1:]).strip()
        if line.startswith('\t') and device in line and current_vendor == vendor:
            return ' '.join(line.split()[1:]).strip()
    
    return ''

def format_data(pci_info, view_format):
    if view_format == 'raw':
        return
    elif view_format == 'string':
        for pci in pci_info:
            pci['Vendor'] = get_vendor(pci['Vendor'])
            pci['Subvendor'] = get_vendor(pci['Subvendor'])
            pci['Device'] = get_device(pci['Device'], pci['Vendor'])
            pci['Subdevice'] = get_device(pci['Subdevice'], pci['Subvendor'])
            
            if pci['Vendor'] == pci['Subvendor']:
                pci['Subvendor'] = ''
            if pci['Device'] == pci['Subdevice']:
                pci['Subdevice'] = ''
    else:
        print(f'invalid view format: {view_format}')
        exit(1)

def format_pci_output(pci_info, lines, output_format):
    if output_format == 'raw':
        for line in lines:
            print(line.replace('\n', ''))
    elif output_format == 'json':
        json_data = json.dumps(pci_info, indent=2)
        print(json_data)
    elif output_format == 'classic':
        for pci in pci_info:
            print(f"{pci['Name'].replace('0000:', '')} {pci['Class']}: {pci['Vendor']} {pci['Device']} {'(rev ' + pci['Revision'] + ')' if pci['Revision'] != '00' else ''}")
    elif output_format == 'full':
        for pci in pci_info:
            print(f"{pci['Name']} {pci['Class']}: {pci['Vendor'] if pci['Vendor'] == pci['Subvendor'] or pci['Subvendor'] == '' else pci['Vendor'] + ' ' + pci['Subvendor']} {pci['Device'] if pci['Device'] == pci['Subdevice'] or pci['Subdevice'] == '' else pci['Device'] + ' ' + pci['Subdevice']} {'(rev ' + pci['Revision'] + ')' if pci['Revision'] != '00' else ''}")
    else:
        print(f'invalid output format: {output_format}')
        exit(1)

def get_permission(perms, name):
    permissions = {'0': '---', '1': '--x', '2': '-w-', '3': 'r--', '4': '-wx', '5': 'r-x', '6': 'rw-', '7': 'rwx'}
    res = 'd' if '.' not in name else '-'
    
    for perm in perms:
        res += permissions[perm]
    
    return res  

def convert_time(time_in_seconds):
    current_datetime = datetime.datetime(1970, 1, 1) + datetime.timedelta(seconds=int(time_in_seconds))
    current_datetime = current_datetime + datetime.timedelta(hours=3) # Moscow hours
    str_time = current_datetime.strftime('%b %d %H:%M')
    return str_time

def format_dentry_output(dentry_info, lines, output_format):
    if output_format == 'raw':
        for line in lines:
            print(line.replace('\n', ''))
    elif output_format == 'json':
        json_data = json.dumps(dentry_info[0], indent=2)
        print(json_data)
    elif output_format == 'classic':
        for dentry in dentry_info:
            print(f"{dentry['Inode']} {get_permission(dentry['Permissions'], dentry['Name'])} {dentry['Link count']} {dentry['Size']} {convert_time(dentry['Modify time'])} {dentry['Name']}")    
    elif output_format == 'full':
        for dentry in dentry_info:
            print(f"{dentry['Parent']} {dentry['Inode']} {dentry['Name']} {get_permission(dentry['Permissions'], dentry['Name'])} {dentry['Size']} {dentry['Link count']}  Modified: {convert_time(dentry['Modify time'])} Changed: {convert_time(dentry['Change time'])} Accessed: {convert_time(dentry['Access time'])}")    
    else:
        print(f'invalid output format: {output_format}')
        exit(1)


pci_file_path = '/proc/pciinfo'
dentry_file_path = '/proc/dentryinfo'

parser = argparse.ArgumentParser(description='PCI And Dentry Parser')
parser.add_argument('command', choices=['dentry', 'pci'], default='dentry', help='Command')
parser.add_argument('-path', required='dentry' in sys.argv, default='/home', help='Path to dentry (only for dentry command)')
parser.add_argument('-view', required='pci' in sys.argv, choices=['raw', 'string'], default='string', help='View format for pci, don\'t work with raw format')
parser.add_argument('-format', choices=['classic', 'json', 'raw', 'full'], default='classic', help='Output format')
args = parser.parse_args()

if args.command == 'pci':
    pci_info, lines = parse_info(pci_file_path)
    format_data(pci_info, args.view)
    format_pci_output(pci_info, lines, args.format)
elif args.command == 'dentry':
    fd = os.open(dentry_file_path, os.O_WRONLY)
    if fd == -1:
        sys.exit(1)

    os.write(fd, str(args.path).encode())
    os.close(fd)

    dentry_info, lines = parse_info(dentry_file_path)
    format_dentry_output(dentry_info, lines, args.format)
else:
    print('unknown command, choose [dentry] or [pci]')
        