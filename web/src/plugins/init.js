Array.prototype.consists = function(object) {
    for (let item of this) {
        if (item === object) {
            return true;
        }
    }
    return false;
}
Array.prototype.startWith = function(keyword) {
    for (let item of this) {
        if (item.startsWith(keyword)) {
            return true;
        }
    }
    return false;
}