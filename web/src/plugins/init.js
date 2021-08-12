Array.prototype.consists = function(object) {
    for (let item of this) {
        if (item === object) {
            return true;
        }
    }
    return false;
}