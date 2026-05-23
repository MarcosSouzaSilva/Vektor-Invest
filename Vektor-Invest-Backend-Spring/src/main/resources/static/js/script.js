// Script.js

// Dados simulados de ações brasileiras (apenas para exemplo de estrutura, não é sensível)
const stockDatabase = {
    'PETR4': {
        name: 'Petrobras PN',
        price: 32.45,
        change: 2.3,
        volume: '1.2M',
        pe: 6.8,
        sector: 'Petróleo'
    },
    'VALE3': {
        name: 'Vale ON',
        price: 68.90,
        change: -1.2,
        volume: '890K',
        pe: 4.2,
        sector: 'Mineração'
    },
    'ITUB4': {
        name: 'Itaú Unibanco PN',
        price: 25.67,
        change: 0.8,
        volume: '2.1M',
        pe: 7.5,
        sector: 'Bancos'
    },
    'BBDC4': {
        name: 'Bradesco PN',
        price: 15.23,
        change: -0.5,
        volume: '1.8M',
        pe: 6.9,
        sector: 'Bancos'
    },
    'ABEV3': {
        name: 'Ambev ON',
        price: 11.45,
        change: 1.5,
        volume: '3.2M',
        pe: 12.3,
        sector: 'Bebidas'
    },
    'MGLU3': {
        name: 'Magazine Luiza ON',
        price: 8.76,
        change: -3.2,
        volume: '4.5M',
        pe: -15.2,
        sector: 'Varejo'
    },
    'WEGE3': {
        name: 'WEG ON',
        price: 45.89,
        change: 2.1,
        volume: '567K',
        pe: 18.5,
        sector: 'Máquinas'
    },
    'SUZB3': {
        name: 'Suzano ON',
        price: 52.34,
        change: 1.8,
        volume: '678K',
        pe: 8.9,
        sector: 'Papel e Celulose'
    }
};

// Theme Management
function initializeTheme() {
    const savedTheme = localStorage.getItem('theme') || 'light';
    document.documentElement.setAttribute('data-theme', savedTheme);
}



function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark';

    document.documentElement.setAttribute('data-theme', newTheme);
    localStorage.setItem('theme', newTheme);

    // Adicionar efeito de transição suave
    document.body.style.transition = 'background-color 0.3s ease, color 0.3s ease';
    setTimeout(() => {
        document.body.style.transition = '';
    }, 300);
}


// Função para gerar indicadores técnicos aleatórios
function generateTechnicalIndicators() {
    return {
        rsi: Math.floor(Math.random() * 100),
        macd: Math.random() > 0.5 ? 'bullish' : 'bearish',
        ma: Math.random() > 0.5 ? 'bullish' : 'bearish'
    };
}

// Função para determinar recomendação baseada nos dados
function getRecommendation(stockData, indicators) {
    let score = 0;
    let reasons = [];

    // Análise do P/L
    if (stockData.pe > 0 && stockData.pe < 10) {
        score += 2;
        reasons.push('P/L atrativo');
    } else if (stockData.pe > 20) {
        score -= 1;
        reasons.push('P/L elevado');
    }

    // Análise da variação
    if (stockData.change > 0) {
        score += 1;
        reasons.push('tendência positiva');
    } else if (stockData.change < -2) {
        score -= 1;
        reasons.push('queda significativa');
    }

    // Análise do RSI
    if (indicators.rsi < 30) {
        score += 2;
        reasons.push('sobrevenda pelo RSI');
    } else if (indicators.rsi > 70) {
        score -= 1;
        reasons.push('sobrecompra pelo RSI');
    }

    // Análise do MACD
    if (indicators.macd === 'bullish') {
        score += 1;
        reasons.push('MACD positivo');
    } else {
        score -= 1;
        reasons.push('MACD negativo');
    }

    // Determinar recomendação
    if (score >= 3) {
        return {
            action: 'buy',
            text: 'COMPRAR',
            reason: `Recomendamos a compra desta ação devido aos seguintes fatores: ${reasons.join(', ')}.`
        };
    } else if (score <= -2) {
        return {
            action: 'sell',
            text: 'VENDER',
            reason: `Recomendamos cautela ou venda devido aos seguintes fatores: ${reasons.join(', ')}.`
        };
    } else {
        return {
            action: 'hold',
            text: 'MANTER',
            reason: `Posição neutra. Fatores considerados: ${reasons.join(', ')}. Aguarde melhor momento para entrada.`
        };
    }
}

// Função para scroll suave até a seção de análise
function scrollToAnalysis() {
    document.getElementById('analise').scrollIntoView({
        behavior: 'smooth'
    });
}

// Função principal para analisar ação
function analyzeStock(event) {
    event.preventDefault();

    const symbolInput = document.getElementById('stockSymbol');
    const symbol = symbolInput.value.toUpperCase().trim();

    if (!symbol) {
        alert('Por favor, digite um símbolo de ação válido.');
        return;
    }

    // Mostrar loading
    showLoading();
    hideResults();

    // Simular delay de requisição
    setTimeout(() => {
        const stockData = stockDatabase[symbol];

        if (!stockData) {
            hideLoading();
            alert('Ação não encontrada. Tente símbolos como PETR4, VALE3, ITUB4, etc.');
            return;
        }

        const indicators = generateTechnicalIndicators();
        const recommendation = getRecommendation(stockData, indicators);

        displayResults(symbol, stockData, indicators, recommendation);
        hideLoading();
        showResults();

    }, 2000); // 2 segundos de delay para simular processamento
}

// Funções de controle de visibilidade
function showLoading() {
    document.getElementById('loadingIndicator').classList.remove('hidden');
}

function hideLoading() {
    document.getElementById('loadingIndicator').classList.add('hidden');
}

function showResults() {
    document.getElementById('results').classList.remove('hidden');
}

function hideResults() {
    document.getElementById('results').classList.add('hidden');
}

// Função para exibir resultados
function displayResults(symbol, stockData, indicators, recommendation) {
    // Informações básicas da ação
    document.getElementById('stockName').textContent = stockData.name;
    document.getElementById('stockSymbolDisplay').textContent = symbol;

    // Métricas financeiras
    document.getElementById('currentPrice').textContent = `R$ ${stockData.price.toFixed(2)}`;

    const changeElement = document.getElementById('priceChange');
    const changeValue = stockData.change;
    changeElement.textContent = `${changeValue > 0 ? '+' : ''}${changeValue.toFixed(2)}%`;
    changeElement.className = `metric-value ${changeValue > 0 ? 'positive' : 'negative'}`;

    document.getElementById('volume').textContent = stockData.volume;
    document.getElementById('peRatio').textContent = stockData.pe > 0 ? stockData.pe.toFixed(1) : 'N/A';

    // Recomendação
    const recommendationBadge = document.getElementById('recommendationBadge');
    recommendationBadge.className = `recommendation-badge ${recommendation.action}`;
    document.getElementById('recommendationText').textContent = recommendation.text;
    document.getElementById('recommendationReason').textContent = recommendation.reason;

    // Indicadores técnicos
    const rsiBar = document.getElementById('rsiBar');
    const rsiValue = document.getElementById('rsiValue');
    rsiBar.style.width = `${indicators.rsi}%`;
    rsiValue.textContent = indicators.rsi;

    // Cor do RSI baseada no valor
    if (indicators.rsi < 30) {
        rsiValue.className = 'indicator-value positive';
    } else if (indicators.rsi > 70) {
        rsiValue.className = 'indicator-value negative';
    } else {
        rsiValue.className = 'indicator-value';
    }

    // MACD
    const macdElement = document.getElementById('macdValue');
    macdElement.textContent = indicators.macd === 'bullish' ? 'Alta' : 'Baixa';
    macdElement.className = `indicator-status ${indicators.macd}`;

    // Média Móvel
    const maElement = document.getElementById('maValue');
    maElement.textContent = indicators.ma === 'bullish' ? 'Alta' : 'Baixa';
    maElement.className = `indicator-status ${indicators.ma}`;
}

// Check if user is logged in and update navigation (Removed sensitive checks)
function updateNavigation() {
    const navLinks = document.querySelector('.nav-links');

    // Example of adding a profile link if a user were logged in (no sensitive data)
    // This part would ideally be handled by the backend rendering the correct navigation.
    if (!navLinks.querySelector('.profile-link')) {
        const profileLink = document.createElement('a');


        // Insert before theme toggle
        const themeToggle = navLinks.querySelector('.theme-toggle');
        navLinks.insertBefore(profileLink, themeToggle);
    }
}

// Event listeners
document.addEventListener('DOMContentLoaded', function() {
    // Inicializar tema
    initializeTheme();

    // Update navigation based on login status
    updateNavigation();

    // Smooth scrolling para links de navegação
    const navLinks = document.querySelectorAll('.nav-links a[href^="#"]');
    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const targetId = this.getAttribute('href');
            const targetSection = document.querySelector(targetId);
            if (targetSection) {
                targetSection.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });

    // Animação das barras do gráfico no hero
    const bars = document.querySelectorAll('.bar');
    bars.forEach((bar, index) => {
        bar.style.animationDelay = `${index * 0.1}s`;
    });

    // Focus no input quando a página carrega
    const stockInput = document.getElementById('stockSymbol');
    if (stockInput) {
        // Focar no input após um pequeno delay
        setTimeout(() => {
            stockInput.focus();
        }, 500);
    }
});

// Adicionar funcionalidade do menu mobile (toggle)
document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.querySelector('.menu-toggle');
    const navLinks = document.querySelector('.nav-links');

    if (menuToggle && navLinks) {
        menuToggle.addEventListener('click', function() {
            navLinks.classList.toggle('active');
            this.classList.toggle('active');
        });
    }
});

// Adicionar efeito de digitação no hero
function typeWriter(element, text, speed = 100) {
    let i = 0;
    element.innerHTML = '';

    function type() {
        if (i < text.length) {
            element.innerHTML += text.charAt(i);
            i++;
            setTimeout(type, speed);
        }
    }

    type();
}

// Ativar efeito de digitação quando a página carrega
document.addEventListener('DOMContentLoaded', function() {
    const heroTitle = document.querySelector('.hero-title');
    if (heroTitle) {
        const originalText = heroTitle.textContent;
        setTimeout(() => {
            typeWriter(heroTitle, originalText, 80);
        }, 1000);
    }
});

// Adicionar efeito de scroll para revelar elementos
function revealOnScroll() {
    const reveals = document.querySelectorAll('.feature-card, .metric, .stock-card');

    for (let i = 0; i < reveals.length; i++) {
        const windowHeight = window.innerHeight;
        const elementTop = reveals[i].getBoundingClientRect().top;
        const elementVisible = 150;

        if (elementTop < windowHeight - elementVisible) {
            reveals[i].classList.add('active');
        } else {
            reveals[i].classList.remove('active');
        }
    }
}

window.addEventListener('scroll', revealOnScroll);

// Adicionar validação em tempo real no input
document.addEventListener('DOMContentLoaded', function() {
    const stockInput = document.getElementById('stockSymbol');

    if (stockInput) {
        stockInput.addEventListener('input', function() {
            let value = this.value.toUpperCase();
            // Remover caracteres que não são letras ou números
            value = value.replace(/[^A-Z0-9]/g, '');
            // Limitar a 6 caracteres
            if (value.length > 6) {
                value = value.substring(0, 6);
            }
            this.value = value;
        });

        // Permitir análise ao pressionar Enter
        stockInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                analyzeStock(e);
            }
        });
    }
});

// Adicionar animação de counter para números
function animateValue(element, start, end, duration) {
    let startTimestamp = null;
    const step = (timestamp) => {
        if (!startTimestamp) startTimestamp = timestamp;
        const progress = Math.min((timestamp - startTimestamp) / duration, 1);
        const value = progress * (end - start) + start;
        element.innerHTML = value.toFixed(2);
        if (progress < 1) {
            window.requestAnimationFrame(step);
        }
    };
    window.requestAnimationFrame(step);
}

// Função para adicionar efeitos visuais aos cards de métricas
function addMetricAnimations() {
    const metrics = document.querySelectorAll('.metric');
    metrics.forEach((metric, index) => {
        metric.style.animationDelay = `${index * 0.1}s`;
        metric.classList.add('fade-in');
    });
}

// CSS adicional para animações (será aplicado via JavaScript)
const additionalStyles = `
    .fade-in {
        animation: fadeInUp 0.6s ease-out forwards;
        opacity: 0;
        transform: translateY(20px);
    }

    .nav-links.active {
        display: flex;
        position: absolute;
        top: 100%;
        left: 0;
        right: 0;
        background: var(--background);
        box-shadow: var(--shadow);
        flex-direction: column;
        padding: 1rem;
        gap: 1rem;
        border: 1px solid var(--border-color);
    }

    .menu-toggle.active span:nth-child(1) {
        transform: rotate(45deg) translate(5px, 5px);
    }

    .menu-toggle.active span:nth-child(2) {
        opacity: 0;
    }

    .menu-toggle.active span:nth-child(3) {
        transform: rotate(-45deg) translate(7px, -6px);
    }
`;

// Adicionar estilos CSS dinamicamente
const styleSheet = document.createElement('style');
styleSheet.textContent = additionalStyles;
document.head.appendChild(styleSheet);