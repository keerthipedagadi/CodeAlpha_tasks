// 1. Image Database (25 Professional Images)
const galleryData = [
    { id: 0, cat: "nature", url: "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800" },
    { id: 1, cat: "nature", url: "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800" },
    { id: 2, cat: "nature", url: "https://images.unsplash.com/photo-1505118380757-91f5f5632de0?w=800" },
    { id: 3, cat: "nature", url: "https://images.unsplash.com/photo-1473580044384-7ba9967e16a0?w=800" },
    { id: 4, cat: "nature", url: "https://images.unsplash.com/photo-1470770841072-f978cf4d019e?w=800" },
    { id: 5, cat: "architecture", url: "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=800" },
    { id: 6, cat: "architecture", url: "https://images.unsplash.com/photo-1511818966892-d7d671e672a2?w=800" },
    { id: 7, cat: "architecture", url: "https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=800" },
    { id: 8, cat: "architecture", url: "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=800" },
    { id: 9, cat: "architecture", url: "https://images.unsplash.com/photo-1542831371-29b0f74f9713?w=800" },
    { id: 10, cat: "tech", url: "https://images.unsplash.com/photo-1518770660439-4636190af475?w=800" },
    { id: 11, cat: "tech", url: "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=800" },
    { id: 12, cat: "tech", url: "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=800" },
    { id: 13, cat: "tech", url: "https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=800" },
    { id: 14, cat: "tech", url: "https://images.unsplash.com/photo-1550009158-9ebf69173e03?w=800" },
    { id: 15, cat: "nature", url: "https://images.unsplash.com/photo-1447752875215-b2761acb3c5d?w=800" },
    { id: 16, cat: "nature", url: "https://images.unsplash.com/photo-1491002052546-bf38f186af56?w=800" },
    { id: 17, cat: "nature", url: "https://images.unsplash.com/photo-1474044159687-1ee9f3a51722?w=800" },
    { id: 18, cat: "nature", url: "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=800" },
    { id: 19, cat: "architecture", url: "https://images.unsplash.com/photo-1449034446853-66c86144b0ad?w=800" },
    { id: 20, cat: "architecture", url: "https://images.unsplash.com/photo-1470075801209-17f9ec0cada6?w=800" }, // FIXED IMAGE
    { id: 21, cat: "architecture", url: "https://images.unsplash.com/photo-1487958449943-2429e8be8625?w=800" }, // FIXED IMAGE
    { id: 22, cat: "tech", url: "https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?w=800" },
    { id: 23, cat: "tech", url: "https://images.unsplash.com/photo-1491336477066-31156b5e4f35?w=800" },
    { id: 24, cat: "tech", url: "https://images.unsplash.com/photo-1622979135225-d2ba269cf1ac?w=800" }
];

window.addEventListener('DOMContentLoaded', () => {
    UI.init();
});

const UI = {
    grid: document.getElementById('gallery-grid'),
    filterContainer: document.getElementById('filter-container'),
    lightbox: document.getElementById('lightbox-portal'),
    lbImg: document.getElementById('active-lightbox-img'),

    init() {
        this.renderFilters();
        this.renderGallery('all');
        this.bindEvents();
    },

    renderFilters() {
        const categories = ['all', 'nature', 'architecture', 'tech'];
        if(this.filterContainer) {
            this.filterContainer.innerHTML = categories.map(cat => 
                `<button class="filter-btn ${cat==='all'?'active':''}" data-filter="${cat}">${cat}</button>`
            ).join('');
        }
    },

    renderGallery(filter) {
        if(!this.grid) return;
        this.grid.innerHTML = '';
        const filtered = filter === 'all' ? galleryData : galleryData.filter(i => i.cat === filter);
        
        filtered.forEach((item) => {
            const card = document.createElement('div');
            card.className = 'gallery-card';
            card.innerHTML = `<img src="${item.url}" alt="Gallery Image">`;
            card.onclick = () => Logic.openLightbox(item.id);
            this.grid.appendChild(card);
        });
    },

    bindEvents() {
        if(this.filterContainer) {
            this.filterContainer.addEventListener('click', (e) => {
                if(e.target.classList.contains('filter-btn')) {
                    document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
                    e.target.classList.add('active');
                    this.renderGallery(e.target.dataset.filter);
                }
            });
        }
        
        if(this.lightbox) {
            this.lightbox.addEventListener('click', (e) => {
                if (e.target === this.lightbox || e.target.classList.contains('lightbox-content')) {
                    Logic.closeLightbox();
                }
            });
        }
    }
};

const Logic = {
    currentId: 0,
    openLightbox(id) {
        this.currentId = id;
        this.updateView();
        UI.lightbox.style.display = 'block';
        document.body.style.overflow = 'hidden';
    },
    closeLightbox() {
        UI.lightbox.style.display = 'none';
        document.body.style.overflow = 'auto';
    },
    nextImage() {
        // Find index of current ID to handle navigation correctly even when filtered
        const currentIndex = galleryData.findIndex(i => i.id === this.currentId);
        const nextIndex = (currentIndex + 1) % galleryData.length;
        this.currentId = galleryData[nextIndex].id;
        this.updateView();
    },
    prevImage() {
        const currentIndex = galleryData.findIndex(i => i.id === this.currentId);
        const prevIndex = (currentIndex - 1 + galleryData.length) % galleryData.length;
        this.currentId = galleryData[prevIndex].id;
        this.updateView();
    },
    updateView() {
        const item = galleryData.find(i => i.id === this.currentId);
        if(item) UI.lbImg.src = item.url;
    }
};