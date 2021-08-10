export default {
    base_url: process.env.NODE_ENV === 'production' ? './api' : 'http://localhost:7743/api',
    file_type: {
        code: ['java', 'py', 'txt', 'conf', 'vue', 'html', 'css', 'js', 'md', 'sql'],
        img: ['jpg', 'jpeg', 'png', 'gif', 'webp'],
        video: ['mp4']
    }
}