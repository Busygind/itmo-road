function setTimezone() {
    $('input[id="form:timezone"]').val(new Date().getTimezoneOffset());
}