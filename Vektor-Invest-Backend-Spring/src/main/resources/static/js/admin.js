// Admin JavaScript Functions

// Toggle password visibility
function togglePassword() {
    const passwordInput = document.getElementById('password');
    const passwordIcon = document.getElementById('passwordIcon');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        passwordIcon.textContent = '🙈';
    } else {
        passwordInput.type = 'password';
        passwordIcon.textContent = '👁️';
    }
}

// Dashboard tab switching
function switchTab(tabName) {
    // Hide all tab contents
    const tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(content => {
        content.classList.remove('active');
    });

    // Remove active class from all tab buttons
    const tabButtons = document.querySelectorAll('.tab-btn');
    tabButtons.forEach(button => {
        button.classList.remove('active');
    });

    // Show selected tab content
    document.getElementById(tabName).classList.add('active');

    // Add active class to clicked button
    event.target.classList.add('active');

    // Animate chart bars if analytics tab is selected
    if (tabName === 'analytics') {
        setTimeout(() => {
            animateChartBars();
        }, 100);
    }
}

// Animate chart bars
function animateChartBars() {
    const bars = document.querySelectorAll('.chart-bar');
    bars.forEach((bar, index) => {
        bar.style.height = '0%';
        setTimeout(() => {
            const originalHeight = bar.getAttribute('data-value') ?
                bar.getAttribute('data-value') + '%' :
                bar.style.height;
            bar.style.height = originalHeight;
        }, index * 100);
    });
}

// Update dashboard stats (simulate real-time data)
function updateDashboardStats() {
    const stats = {
        totalAnalyses: Math.floor(Math.random() * 100) + 1200,
        activeUsers: Math.floor(Math.random() * 50) + 850,
        systemStatus: (99.5 + Math.random() * 0.4).toFixed(1)
    };

    // Animate counter updates
    animateCounter('totalAnalyses', stats.totalAnalyses);
    animateCounter('activeUsers', stats.activeUsers);

    const statusElement = document.getElementById('systemStatus');
    if (statusElement) {
        statusElement.textContent = stats.systemStatus + '%';
    }
}

// Animate counter values
function animateCounter(elementId, targetValue) {
    const element = document.getElementById(elementId);
    if (!element) return;

    const currentValue = parseInt(element.textContent.replace(/[^\d]/g, ''));
    const increment = (targetValue - currentValue) / 20;
    let current = currentValue;

    const timer = setInterval(() => {
        current += increment;
        if ((increment > 0 && current >= targetValue) ||
            (increment < 0 && current <= targetValue)) {
            current = targetValue;
            clearInterval(timer);
        }

        element.textContent = Math.floor(current).toLocaleString('pt-BR');
    }, 50);
}


// Settings slider updates
function updateSliderValue(slider, valueSpan) {
    valueSpan.textContent = slider.value + '%';
}

// Initialize admin functionality
document.addEventListener('DOMContentLoaded', function () {
    // Initialize dashboard if on dashboard page
    if (window.location.pathname.includes('adminDashboard.html')) {
        // Update stats periodically
        updateDashboardStats();
        setInterval(updateDashboardStats, 30000); // Update every 30 seconds

        // Initialize chart animation
        setTimeout(() => {
            animateChartBars();
        }, 500);

        // Add slider event listeners
        const sliders = document.querySelectorAll('.slider');
        sliders.forEach(slider => {
            const valueSpan = slider.parentElement.querySelector('span:last-child');
            if (valueSpan) {
                slider.addEventListener('input', () => {
                    updateSliderValue(slider, valueSpan);
                });
            }
        });

        // Add hover effects to chart bars
        const chartBars = document.querySelectorAll('.chart-bar');
        chartBars.forEach(bar => {
            bar.addEventListener('mouseenter', function () {
                const value = this.getAttribute('data-value') || '0';
                this.setAttribute('title', `${value} análises`);
            });
        });
    }
});


// Add real-time clock to dashboard
function updateClock() {
    const now = new Date();
    const timeString = now.toLocaleTimeString('pt-BR');
    const dateString = now.toLocaleDateString('pt-BR');

    // You can add a clock element to the dashboard header if needed
    const clockElement = document.getElementById('dashboardClock');
    if (clockElement) {
        clockElement.textContent = `${dateString} ${timeString}`;
    }
}

// Update clock every second if on dashboard
if (window.location.pathname.includes('adminDashboard.html')) {
    setInterval(updateClock, 1000);
}

// Add notification system (placeholder)
function showNotification(message, type = 'info') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;

    // Style the notification
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background: var(--primary-color);
        color: white;
        padding: 1rem 1.5rem;
        border-radius: var(--border-radius);
        box-shadow: var(--shadow-lg);
        z-index: 10000;
        transform: translateX(100%);
        transition: transform 0.3s ease;
    `;

    if (type === 'error') {
        notification.style.background = 'var(--error-color)';
    } else if (type === 'success') {
        notification.style.background = 'var(--secondary-color)';
    }

    document.body.appendChild(notification);

    // Animate in
    setTimeout(() => {
        notification.style.transform = 'translateX(0)';
    }, 100);

    // Remove after 3 seconds
    setTimeout(() => {
        notification.style.transform = 'translateX(100%)';
        setTimeout(() => {
            document.body.removeChild(notification);
        }, 300);
    }, 3000);
}


// Profile tab switching
function switchProfileTab(tabName) {
    // Hide all tab contents
    const tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(content => {
        content.classList.remove('active');
    });

    // Remove active class from all tab buttons
    const tabButtons = document.querySelectorAll('.tab-btn');
    tabButtons.forEach(button => {
        button.classList.remove('active');
    });

    // Show selected tab content
    document.getElementById(tabName).classList.add('active');

    // Add active class to clicked button
    event.target.classList.add('active');
}

function showAddStockModal() {
    const modal = document.getElementById('addStockModal');
    modal.style.display = 'flex'; // Exibe o modal como flex para centralização
}

function showEditStockModal() {
    const modal = document.getElementById('editStockModal');
    modal.style.display = 'flex'; // Exibe o modal como flex para centralização
}

function showEditUserModal() {
    const modal = document.getElementById('editUserModal');
    modal.style.display = 'flex'; // Exibe o modal como flex para centralização
}

function closeAddStockModal() {
    const modal = document.getElementById('addStockModal');
    modal.style.display = 'none'; // Oculta o modal
    document.getElementById('addStockForm').reset(); // Opcional: limpa o formulário ao fechar
}

// Opcional: Fechar modal clicando fora
window.onclick = function(event) {
    const modal = document.getElementById('addStockModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}

// Lógica para enviar o formulário (exemplo - você precisará implementar a integração com o backend)
document.getElementById('addStockForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio padrão do formulário

    const stockSymbol = document.getElementById('stockSymbol').value;
    const stockName = document.getElementById('stockName').value;
    const stockPrice = document.getElementById('stockPrice').value;
    const stockStatus = document.getElementById('stockStatus').value;

    console.log('Nova Ação a ser adicionada:');
    console.log('Símbolo:', stockSymbol);
    console.log('Nome:', stockName);
    console.log('Preço:', stockPrice);
    console.log('Status:', stockStatus);

    // Aqui você faria uma requisição AJAX (fetch ou XMLHttpRequest) para o seu backend
    // Por exemplo:
    /*
    fetch('/api/stocks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            symbol: stockSymbol,
            name: stockName,
            price: parseFloat(stockPrice), // Converter para número
            status: stockStatus
        }),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Ação adicionada com sucesso:', data);
        // Atualize a lista de ações na interface se necessário
        closeAddStockModal(); // Fecha o modal após o sucesso
    })
    .catch((error) => {
        console.error('Erro ao adicionar ação:', error);
        alert('Erro ao adicionar ação. Tente novamente.');
    });
    */

    // Para fins de demonstração, apenas feche o modal após a "submissão"
    alert('Ação "' + stockName + '" (' + stockSymbol + ') adicionada com sucesso (simulado)!');
    closeAddStockModal();
});

function toggleTheme() {
    // Adiciona ou remove a classe "dark-theme" na tag <body>
    document.body.classList.toggle('dark-theme');

    // Opcional: Salva a preferência do usuário no navegador
    const isDark = document.body.classList.contains('dark-theme');
    localStorage.setItem('theme', isDark ? 'dark' : 'light');
}

// Ao carregar a página, verifica se o usuário já tinha escolhido o tema escuro antes
document.addEventListener('DOMContentLoaded', () => {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-theme');
    }
});

// Export functions for global use
window.togglePassword = togglePassword;
window.switchTab = switchTab;
window.showAddUserModal = showAddUserModal;
window.showAddStockModal = showAddStockModal;
window.switchProfileTab = switchProfileTab;
window.editProfile = editProfile;
window.changePassword = changePassword;