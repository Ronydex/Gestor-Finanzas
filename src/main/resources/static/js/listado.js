// ==========================================
// MÓDULO 0: Inicialización Inmediata del Tema
// ==========================================
(function() {
    const temaGuardado = localStorage.getItem('tema') || 'light';
    document.documentElement.setAttribute('data-bs-theme', temaGuardado);
})();

document.addEventListener("DOMContentLoaded", function () {
    
    // Recuperar datos del puente de Thymeleaf de forma segura
    const config = window.DashboardConfig || { etiquetas: [], valores: [], tipos: [], errorMensaje: null };

    // ==========================================
    // MÓDULO 1: Alternador de Tema (Dark / Light)
    // ==========================================
    const htmlElement = document.documentElement;
    const themeBtn = document.getElementById('btn-theme');
    const themeIcon = document.getElementById('theme-icon');

    if (themeBtn && themeIcon) {
        // Inicializar el icono según el tema cargado inmediatamente
        actualizarIcono(htmlElement.getAttribute('data-bs-theme'));

        themeBtn.addEventListener('click', () => {
            const temaActual = htmlElement.getAttribute('data-bs-theme');
            const nuevoTema = (temaActual === 'light') ? 'dark' : 'light';
            
            htmlElement.setAttribute('data-bs-theme', nuevoTema);
            localStorage.setItem('tema', nuevoTema);
            actualizarIcono(nuevoTema);
        });
    }

    function actualizarIcono(tema) {
        if (!themeIcon) return;
        if (tema === 'dark') {
            themeIcon.className = 'bi bi-sun-fill';
        } else {
            themeIcon.className = 'bi bi-moon-fill';
        }
    }

    // ==========================================
    // MÓDULO 2: Renderizado de Chart.js
    // ==========================================
    const canvas = document.getElementById('miGraficaDeGastos');
    
    if (canvas && config.etiquetas.length > 0) {
        // Mapeo dinámico de colores según el tipo de transacción de Kakeibo
        const coloresFondo = config.tipos.map(t => t === 'INGRESO' ? 'rgba(40, 167, 69, 0.7)' : 'rgba(220, 53, 69, 0.7)');
        const coloresBorde = config.tipos.map(t => t === 'INGRESO' ? 'rgba(40, 167, 69, 1)' : 'rgba(220, 53, 69, 1)');

        try {
            const ctx = canvas.getContext('2d');
            new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: config.etiquetas,
                    datasets: [{
                        data: config.valores,
                        backgroundColor: coloresFondo,
                        borderColor: coloresBorde,
                        borderWidth: 2,
                        hoverOffset: 15
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: { position: 'bottom' }
                    }
                }
            });
        } catch (err) {
            console.error("Error al inicializar la gráfica de dona:", err);
        }
    }

    // ==========================================
    // MÓDULO 3: Captura de Errores Globales (Backend Exception)
    // ==========================================
    if (config.errorMensaje) {
        Swal.fire({
            icon: 'error',
            title: '¡Ups!',
            text: config.errorMensaje,
            confirmButtonColor: '#3085d6'
        });
    }
});

// ==========================================
// MÓDULO 4: Funciones Globales (Acceso desde el HTML)
// ==========================================
// Al estar en un archivo externo, necesitamos adjuntar la función al objeto global 'window'
// para que las etiquetas HTML con "th:onclick" o "onclick" sigan encontrando el método.
window.confirmarBorrado = function(id) {
    Swal.fire({
        title: '¿Eliminar registro?',
        text: "Esta acción no se puede deshacer",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, borrarlo',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = '/gastos/eliminar/' + id;
        }
    });
};
