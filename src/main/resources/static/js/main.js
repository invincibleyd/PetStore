// Removed theme toggle functionality for uniform theme
// Small utilities for UI enhancements
(function(){
  // Add smooth scrolling for anchor links
  document.addEventListener('click', (e) => {
    if (e.target.matches('a[href^="#"]')) {
      e.preventDefault();
      const target = document.querySelector(e.target.getAttribute('href'));
      if (target) {
        target.scrollIntoView({ behavior: 'smooth' });
      }
    }
  });

  // Add loading states for buttons
  document.addEventListener('click', (e) => {
    if (e.target.matches('.btn')) {
      const btn = e.target;
      if (!btn.classList.contains('loading')) {
        btn.classList.add('loading');
        setTimeout(() => btn.classList.remove('loading'), 1000);
      }
    }
  });

  // Initialize tooltips if Bootstrap tooltips are used
  const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
  if (typeof bootstrap !== 'undefined' && bootstrap.Tooltip) {
    tooltipTriggerList.map(function (tooltipTriggerEl) {
      return new bootstrap.Tooltip(tooltipTriggerEl);
    });
  }
})();
  