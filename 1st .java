/* ============================================================
   Lumière Image Gallery — script.js
============================================================ */

/* ============================================================
   IMAGE DATA
   Each entry: { src, title, cat, desc }
   Categories: nature | city | portrait | abstract | architecture
============================================================ */
const DATA = [
  { src:"https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&q=75", title:"Mountain Silence",  cat:"nature",       desc:"Patagonia, Argentina — 2023" },
  { src:"https://images.unsplash.com/photo-1494522855154-9297ac14b55f?w=400&q=75", title:"Ocean Horizon",     cat:"nature",       desc:"Iceland — Summer solstice" },
  { src:"https://images.unsplash.com/photo-1473448912268-2022ce9509d8?w=400&q=75", title:"Autumn Drift",      cat:"nature",       desc:"Bavaria, Germany — October" },
  { src:"https://images.unsplash.com/photo-1416879595882-3373a0480b5b?w=400&q=75", title:"Botanical Dream",   cat:"nature",       desc:"Amsterdam — Keukenhof Gardens" },
  { src:"https://images.unsplash.com/photo-1500534314209-a25ddb2bd429?w=400&q=75", title:"Forest Light",      cat:"nature",       desc:"Pacific Northwest — Dawn" },
  { src:"https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=400&q=75", title:"Valley Mist",       cat:"nature",       desc:"Swiss Alps — Early morning" },
  { src:"https://images.unsplash.com/photo-1433086966358-54859d0ed716?w=400&q=75", title:"Waterfall Veil",    cat:"nature",       desc:"Norway — Hidden cascade" },
  { src:"https://images.unsplash.com/photo-1504700610630-ac6aba3536d3?w=400&q=75", title:"Desert Dunes",      cat:"nature",       desc:"Sahara — Golden hour" },
  { src:"https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&q=75", title:"Wildflower Field",  cat:"nature",       desc:"Tuscany — Spring bloom" },

  { src:"https://images.unsplash.com/photo-1477959858617-67f85cf4f1df?w=400&q=75", title:"City Lights",       cat:"city",         desc:"New York, USA — Night exposure" },
  { src:"https://images.unsplash.com/photo-1486325212027-8081e485255e?w=400&q=75", title:"Metro Rush",        cat:"city",         desc:"Tokyo, Japan — 2022" },
  { src:"https://images.unsplash.com/photo-1444723121867-7a241cacace9?w=400&q=75", title:"Urban Grid",        cat:"city",         desc:"Hong Kong — Aerial view" },
  { src:"https://images.unsplash.com/photo-1542051841857-5f90071e7989?w=400&q=75", title:"Neon Alley",        cat:"city",         desc:"Tokyo, Japan — Shinjuku night" },
  { src:"https://images.unsplash.com/photo-1480714378408-67cf0d13bc1b?w=400&q=75", title:"Bridge at Dusk",    cat:"city",         desc:"San Francisco — Golden Gate" },
  { src:"https://images.unsplash.com/photo-1513635269975-59663e0ac1ad?w=400&q=75", title:"London Blur",       cat:"city",         desc:"London — Long exposure" },
  { src:"https://images.unsplash.com/photo-1534430480872-3498386e7856?w=400&q=75", title:"Rain Street",       cat:"city",         desc:"Paris — Wet pavements" },

  { src:"https://images.unsplash.com/photo-1531746020798-e6953c6e8e04?w=400&q=75", title:"Golden Gaze",       cat:"portrait",     desc:"Studio — Natural light portrait" },
  { src:"https://images.unsplash.com/photo-1554080353-a576cf803bda?w=400&q=75", title:"Soft Focus",        cat:"portrait",     desc:"Analog — 35mm film" },
  { src:"https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?w=400&q=75", title:"Window Light",      cat:"portrait",     desc:"Documentary — Natural light" },
  { src:"https://images.unsplash.com/photo-1517841905240-472988babdf9?w=400&q=75", title:"Freckled Sun",      cat:"portrait",     desc:"Outdoor — Summer session" },
  { src:"https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400&q=75", title:"Studio Contrast",   cat:"portrait",     desc:"Studio — High contrast mono" },
  { src:"https://images.unsplash.com/photo-1524504388940-b1c1722653e1?w=400&q=75", title:"Soft Dreamer",      cat:"portrait",     desc:"Pastel — Film grain" },

  { src:"https://images.unsplash.com/photo-1465101162946-4377e57745c3?w=400&q=75", title:"Deep Space",        cat:"abstract",     desc:"Digital — Long exposure study" },
  { src:"https://images.unsplash.com/photo-1541746972996-4e0b0f43e02a?w=400&q=75", title:"Color Study",       cat:"abstract",     desc:"Acrylic pour — Studio 2023" },
  { src:"https://images.unsplash.com/photo-1519125323398-675f0ddb6308?w=400&q=75", title:"Glass Maze",        cat:"abstract",     desc:"Optical art — Studio" },
  { src:"https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&q=75", title:"Liquid Chrome",     cat:"abstract",     desc:"Macro — Metallic surface" },
  { src:"https://images.unsplash.com/photo-1507908708918-778587c9e563?w=400&q=75", title:"Smoke Form",        cat:"abstract",     desc:"Studio — Smoke photography" },
  { src:"https://images.unsplash.com/photo-1550684848-86a5d8727436?w=400&q=75", title:"Neon Waves",        cat:"abstract",     desc:"Light painting — Long exposure" },

  { src:"https://images.unsplash.com/photo-1555708982-8645ec9ce3cc?w=400&q=75", title:"Steel & Glass",     cat:"architecture", desc:"Chicago — Reflections" },
  { src:"https://images.unsplash.com/photo-1487958449943-2429e8be8625?w=400&q=75", title:"Arch Harmony",      cat:"architecture", desc:"Barcelona — Gaudí influence" },
  { src:"https://images.unsplash.com/photo-1551038247-3d9af20df552?w=400&q=75", title:"Glass Vault",       cat:"architecture", desc:"London — Modern atrium" },
  { src:"https://images.unsplash.com/photo-1510797215324-95aa89f43c33?w=400&q=75", title:"Spiral Stair",      cat:"architecture", desc:"Vatican Museum — Bramante staircase" },

  { src:"https://images.unsplash.com/photo-1519999482648-25049ddd37b1?w=400&q=75", title:"Concrete Lines",    cat:"architecture", desc:"Brutalist — Geometric shadows" },
  { src:"https://images.unsplash.com/photo-1555397430-57791c75748a?w=400&q=75", title:"Dome Interior",     cat:"architecture", desc:"Florence — Palazzo ceiling" },
];

/* ============================================================
   STATE
============================================================ */
let currentFilter   = "all";
let visibleIndices  = DATA.map((_, i) => i);
let lbCurrent       = 0;
let lbOpen          = false;
let activeImgFilter = "";

/* ============================================================
   DOM REFERENCES
============================================================ */
const gallery    = document.getElementById("gallery");
const lightbox   = document.getElementById("lightbox");
const lbImg      = document.getElementById("lb-img");
const lbCat      = document.getElementById("lb-cat");
const lbTitle    = document.getElementById("lb-title");
const lbDesc     = document.getElementById("lb-desc");
const lbDots     = document.getElementById("lb-dots");
const lbPos      = document.getElementById("lb-pos");
const countBadge = document.getElementById("count-badge");
const emptyState = document.getElementById("empty-state");
const kbHint     = document.getElementById("kb-hint");

/* ============================================================
   BUILD GALLERY
   Renders only items matching the current category filter.
============================================================ */
function buildGallery() {
  gallery.innerHTML = "";
  visibleIndices = [];

  DATA.forEach((img, i) => {
    const match = currentFilter === "all" || img.cat === currentFilter;
    if (!match) return;
    visibleIndices.push(i);

    const item = document.createElement("div");
    item.className = "gallery-item";
    item.dataset.index = i;
    item.setAttribute("tabindex", "0");
    item.setAttribute("role", "button");
    item.setAttribute("aria-label", `Open ${img.title}`);
    item.innerHTML = `
      <img
        src="${img.src}"
        alt="${img.title}"
        loading="eager"
        onerror="this.onerror=null;this.src='https://placehold.co/600x400/ece8e1/6b6560?text=Image+Unavailable'"
      />
      <div class="item-overlay">
        <div class="item-expand">
          <svg viewBox="0 0 24 24" fill="none" stroke="#fff" stroke-width="2.2" stroke-linecap="round">
            <path d="M15 3h6v6M9 21H3v-6M21 3l-7 7M3 21l7-7"/>
          </svg>
        </div>
        <div class="item-cat">${img.cat}</div>
        <div class="item-title">${img.title}</div>
      </div>`;

    const openHandler = () => openLightbox(visibleIndices.indexOf(i));
    item.addEventListener("click", openHandler);
    item.addEventListener("keydown", e => {
      if (e.key === "Enter" || e.key === " ") openHandler();
    });

    gallery.appendChild(item);

    // Mark image as loaded to hide shimmer
    const imgEl = item.querySelector("img");
    if (imgEl.complete) {
      imgEl.classList.add("img-loaded");
    } else {
      imgEl.addEventListener("load",  () => imgEl.classList.add("img-loaded"));
      imgEl.addEventListener("error", () => imgEl.classList.add("img-loaded"));
    }
  });

  // Update count badge
  const n = visibleIndices.length;
  countBadge.textContent = `${n} image${n !== 1 ? "s" : ""}`;

  // Show/hide empty state
  emptyState.classList.toggle("show", n === 0);

  // Rebuild lightbox dots for this filtered set
  buildDots();
}

/* ============================================================
   PROGRESS DOTS
============================================================ */
function buildDots() {
  lbDots.innerHTML = "";
  visibleIndices.forEach((_, i) => {
    const dot = document.createElement("div");
    dot.className = "lb-dot" + (i === lbCurrent ? " active" : "");
    dot.addEventListener("click", () => showImage(i));
    lbDots.appendChild(dot);
  });
}

function refreshDots() {
  lbDots.querySelectorAll(".lb-dot").forEach((d, i) => {
    d.classList.toggle("active", i === lbCurrent);
  });
  lbPos.textContent = `${lbCurrent + 1} / ${visibleIndices.length}`;
}

/* ============================================================
   LIGHTBOX — OPEN & CLOSE
============================================================ */
function openLightbox(posInVisible) {
  lbCurrent = posInVisible;
  activeImgFilter = "";
  resetImgFilterBtns();
  showImage(lbCurrent);
  lightbox.classList.add("open");
  document.body.style.overflow = "hidden";
  lbOpen = true;

  // Show keyboard hint toast briefly
  kbHint.classList.add("show");
  clearTimeout(window._kbTimer);
  window._kbTimer = setTimeout(() => kbHint.classList.remove("show"), 3200);
}

function closeLightbox() {
  lightbox.classList.remove("open");
  document.body.style.overflow = "";
  lbOpen = false;
}

/* ============================================================
   SHOW IMAGE (with fade transition)
============================================================ */
function showImage(pos) {
  // Wrap around both ends
  lbCurrent = ((pos % visibleIndices.length) + visibleIndices.length) % visibleIndices.length;
  const img = DATA[visibleIndices[lbCurrent]];

  // Fade out → swap src → fade in
  lbImg.classList.remove("loaded");
  setTimeout(() => {
    lbImg.src = img.src.replace("w=700", "w=1200"); // load higher-res in lightbox
    lbImg.alt = img.title;
    lbImg.className = "lb-img" + (activeImgFilter ? " " + activeImgFilter : "");
    lbImg.onload = () => lbImg.classList.add("loaded");
  }, 130);

  lbCat.textContent   = img.cat;
  lbTitle.textContent = img.title;
  lbDesc.textContent  = img.desc;
  refreshDots();
}

/* ============================================================
   NAVIGATION — Buttons, Keyboard, Touch/Swipe
============================================================ */

// Button controls
document.getElementById("lb-close").addEventListener("click", closeLightbox);
document.getElementById("lb-prev").addEventListener("click", () => showImage(lbCurrent - 1));
document.getElementById("lb-next").addEventListener("click", () => showImage(lbCurrent + 1));

// Click backdrop to close
lightbox.addEventListener("click", e => {
  if (e.target === lightbox) closeLightbox();
});

// Keyboard navigation
document.addEventListener("keydown", e => {
  if (!lbOpen) return;
  if (e.key === "Escape")     closeLightbox();
  if (e.key === "ArrowLeft")  showImage(lbCurrent - 1);
  if (e.key === "ArrowRight") showImage(lbCurrent + 1);
});

// Touch swipe support (mobile)
let touchStartX = 0;
lightbox.addEventListener("touchstart", e => {
  touchStartX = e.touches[0].clientX;
}, { passive: true });

lightbox.addEventListener("touchend", e => {
  const dx = e.changedTouches[0].clientX - touchStartX;
  if (Math.abs(dx) > 50) {
    dx < 0 ? showImage(lbCurrent + 1) : showImage(lbCurrent - 1);
  }
});

/* ============================================================
   CATEGORY FILTER BUTTONS
============================================================ */
document.querySelectorAll(".filter-btn").forEach(btn => {
  btn.addEventListener("click", () => {
    document.querySelectorAll(".filter-btn").forEach(b => b.classList.remove("active"));
    btn.classList.add("active");
    currentFilter = btn.dataset.filter;
    lbCurrent = 0;
    buildGallery();
  });
});

/* ============================================================
   IMAGE CSS FILTER BUTTONS (inside lightbox)
============================================================ */
function resetImgFilterBtns() {
  document.querySelectorAll(".img-filter-btn").forEach(b => b.classList.remove("active"));
  document.querySelector('.img-filter-btn[data-f=""]').classList.add("active");
}

document.querySelectorAll(".img-filter-btn").forEach(btn => {
  btn.addEventListener("click", () => {
    document.querySelectorAll(".img-filter-btn").forEach(b => b.classList.remove("active"));
    btn.classList.add("active");
    activeImgFilter = btn.dataset.f;
    // Swap filter class on the live image
    lbImg.className = "lb-img loaded" + (activeImgFilter ? " " + activeImgFilter : "");
  });
});

/* ============================================================
   INIT
============================================================ */
buildGallery();
