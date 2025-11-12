// 예시: 버튼 클릭 시 콘솔 확인
document.addEventListener('DOMContentLoaded', function() {
    const buttons = document.querySelectorAll('.btn-box .btn');
    buttons.forEach(btn => {
        btn.addEventListener('click', () => {
            console.log(`${btn.textContent} 버튼 클릭`);
        });
    });
});
