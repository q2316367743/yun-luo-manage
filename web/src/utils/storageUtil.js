export function format(data, level){
    if (level){
        if (level === 1){
            return (data / 1024 / 1024 / 1024).toFixed(2) + 'GB';
        }else if (level === 2){
            return (data / 1024 / 1024).toFixed(2) + 'MB';
        }else if (level === 3){
            return (data / 1024).toFixed(2) + 'KB';
        }else if (level === 4){
            return data.toFixed(2) + 'B'
        }
    }else {
        if (data > 1024 * 1024 * 1024){
            return (data / 1024 / 1024 / 1024).toFixed(2) + 'GB';
        }else if (data > 1024 * 1024){
            return (data / 1024 / 1024).toFixed(2) + 'MB';
        }else if (data > 1024){
            return (data / 1024).toFixed(2) + 'KB';
        }else {
            return data.toFixed(2) + 'B'
        }
    }
}