/* ============================================================
   CALC-7 ULTRA — script.js
   ============================================================ */

/* ---------- DOM References ---------- */
const display     = document.getElementById('display');
const expressionEl = document.getElementById('expression');
const historyBar  = document.getElementById('historyBar');
const toastEl     = document.getElementById('toast');

/* ---------- State ---------- */
let current  = '0';   // Current display value (string)
let previous = null;  // Operand stored before operator press
let operator = null;  // Active operator: '+' | '-' | '*' | '/'
let fresh    = false; // If true, next digit starts a fresh number
let history  = [];    // Array of { entry, result }

/* ---------- Constants ---------- */
const OP_SYMBOLS = { '+': '+', '-': '−', '*': '×', '/': '÷' };

/* ============================================================
   DISPLAY HELPERS
   ============================================================ */

/**
 * Format a number value for display.
 * Clips to 10 significant figures; switches to exponential if still too long.
 */
function formatNumber(val) {
  const str = String(val);
  if (str.length > 14 && !isNaN(val)) {
    const n = parseFloat(val);
    let s = parseFloat(n.toPrecision(10)).toString();
    if (s.length > 14) s = n.toExponential(4);
    return s;
  }
  return str;
}

/**
 * Push a value to the display, adjusting font-size automatically.
 * @param {string|number} val   - Value to show
 * @param {boolean}       animate - Whether to play the slide-in animation
 */
function updateDisplay(val, animate = true) {
  display.classList.remove('error', 'small', 'tiny', 'update');

  const str = formatNumber(val);
  if (str.length > 12)     display.classList.add('tiny');
  else if (str.length > 8) display.classList.add('small');

  display.textContent = str;

  if (animate) {
    void display.offsetWidth; // force reflow to restart animation
    display.classList.add('update');
  }
}

/**
 * Show an error message in the display and reset calculator state.
 */
function setError(msg) {
  display.classList.remove('small', 'tiny', 'update');
  display.classList.add('error');
  display.textContent = msg;
  expressionEl.textContent = '';
  current  = '0';
  operator = null;
  previous = null;
  fresh    = true;
  clearActiveOp();
}

/* ============================================================
   CORE CALCULATOR LOGIC
   ============================================================ */

/**
 * Append a digit or decimal point to the current number.
 */
function inputNum(n) {
  if (fresh) {
    // Start a fresh number after an operator or equals press
    current = (n === '.') ? '0.' : n;
    fresh   = false;
  } else {
    if (n === '.' && current.includes('.')) return; // no double dot
    if (current === '0' && n !== '.') {
      current = n;                                  // replace leading zero
    } else if (current.replace('-', '').replace('.', '').length >= 13) {
      return;                                       // digit limit reached
    } else {
      current += n;
    }
  }
  updateDisplay(current);
}

/**
 * Store the current number and set the pending operator.
 * If an operator is already pending, chain-compute first.
 */
function inputOp(op) {
  if (operator && !fresh) compute(true); // chain calculation

  previous = parseFloat(current);
  operator = op;
  fresh    = true;

  // Highlight the active operator button
  document.querySelectorAll('.op').forEach(btn => {
    btn.classList.toggle('active-op', btn.dataset.op === op);
  });

  expressionEl.textContent = `${formatNumber(previous)} ${OP_SYMBOLS[op]}`;
}

/**
 * Execute the pending calculation.
 * @param {boolean} chained - True when called mid-chain (suppress history/pulse)
 */
function compute(chained = false) {
  if (operator === null || previous === null) {
    if (!chained) pulseEquals();
    return;
  }

  const a     = previous;
  const b     = parseFloat(current);
  const opSym = OP_SYMBOLS[operator];

  // Guard: division by zero
  if (operator === '/' && b === 0) {
    setError('ERR: ÷ 0');
    return;
  }

  // Perform arithmetic
  let result;
  switch (operator) {
    case '+': result = a + b; break;
    case '-': result = a - b; break;
    case '*': result = a * b; break;
    case '/': result = a / b; break;
  }

  // Trim floating-point noise
  result = parseFloat(result.toPrecision(12));

  if (!chained) {
    const entry = `${formatNumber(a)} ${opSym} ${formatNumber(b)} = ${formatNumber(result)}`;
    expressionEl.textContent = entry;
    addHistory(entry, result);
  }

  // Update state
  current  = String(result);
  operator = null;
  previous = null;
  fresh    = true;

  clearActiveOp();
  updateDisplay(current);
  if (!chained) pulseEquals();
}

/**
 * Clear all state and reset the display.
 */
function clear() {
  current  = '0';
  previous = null;
  operator = null;
  fresh    = false;
  expressionEl.textContent = '\u00a0'; // non-breaking space to preserve height
  clearActiveOp();
  updateDisplay('0', false);
}

/**
 * Delete the last typed digit.
 */
function backspace() {
  if (fresh || current === '0') return;
  current = (current.length > 1) ? current.slice(0, -1) : '0';
  updateDisplay(current);
}

/**
 * Toggle positive / negative sign of the current number.
 */
function sign() {
  if (current === '0') return;
  current = current.startsWith('-') ? current.slice(1) : '-' + current;
  updateDisplay(current);
}

/**
 * Convert current value to a percentage (÷ 100).
 */
function percent() {
  current = String(parseFloat(current) / 100);
  updateDisplay(current);
}

/* ============================================================
   UI HELPERS
   ============================================================ */

/** Remove the active-op highlight from all operator buttons. */
function clearActiveOp() {
  document.querySelectorAll('.op').forEach(b => b.classList.remove('active-op'));
}

/** Animate the equals button with an outward glow pulse. */
function pulseEquals() {
  const eq = document.querySelector('.equals');
  eq.classList.add('pressed');
  eq.addEventListener('animationend', () => eq.classList.remove('pressed'), { once: true });
}

/* ============================================================
   HISTORY
   ============================================================ */

/**
 * Prepend a completed calculation to the history list and re-render chips.
 */
function addHistory(entry, result) {
  history.unshift({ entry, result });
  if (history.length > 5) history.pop();
  renderHistory();
}

/** Render up to 3 history chips below the display. */
function renderHistory() {
  historyBar.innerHTML = '';
  history.slice(0, 3).forEach(item => {
    const chip = document.createElement('div');
    chip.className   = 'history-chip';
    chip.textContent = item.entry;
    chip.title       = 'Click to restore result';
    chip.addEventListener('click', () => {
      current = String(item.result);
      updateDisplay(current);
      expressionEl.textContent = item.entry;
      fresh = true;
      showToast('RESULT RESTORED');
    });
    historyBar.appendChild(chip);
  });
}

/* ============================================================
   TOAST NOTIFICATIONS
   ============================================================ */

let toastTimer;

function showToast(msg) {
  clearTimeout(toastTimer);
  toastEl.textContent = msg;
  toastEl.classList.add('show');
  toastTimer = setTimeout(() => toastEl.classList.remove('show'), 1600);
}

/* ============================================================
   RIPPLE EFFECT
   ============================================================ */

/**
 * Create a Material-style ripple on a button at the click position.
 * @param {HTMLElement}   btn - The button element
 * @param {MouseEvent|null} e - The click event (null for keyboard-triggered)
 */
function ripple(btn, e) {
  const span = document.createElement('span');
  span.classList.add('ripple');

  const rect = btn.getBoundingClientRect();
  const size = Math.max(rect.width, rect.height);
  const x    = (e && e.clientX ? e.clientX : rect.left + rect.width  / 2) - rect.left - size / 2;
  const y    = (e && e.clientY ? e.clientY : rect.top  + rect.height / 2) - rect.top  - size / 2;

  // Color ripple based on button type
  let color = 'rgba(255, 255, 255, 0.12)';
  if (btn.classList.contains('op'))     color = 'rgba(0, 255, 231, 0.25)';
  if (btn.classList.contains('equals')) color = 'rgba(0, 255, 231, 0.30)';
  if (btn.classList.contains('action')) color = 'rgba(255, 77, 109, 0.25)';

  span.style.cssText =
    `width:${size}px; height:${size}px; left:${x}px; top:${y}px; background:${color}`;

  btn.appendChild(span);
  span.addEventListener('animationend', () => span.remove());
}

/* ============================================================
   EVENT LISTENERS — Mouse / Touch
   ============================================================ */

document.querySelectorAll('button').forEach(btn => {
  btn.addEventListener('click', e => {
    ripple(btn, e);

    if (btn.dataset.num !== undefined) {
      inputNum(btn.dataset.num);
    } else if (btn.dataset.op) {
      inputOp(btn.dataset.op);
    } else {
      switch (btn.dataset.action) {
        case 'equals':   compute();          break;
        case 'clear':    clear();            break;
        case 'backspace': backspace();       break;
        case 'sign':     sign();             break;
        case 'percent':  percent();          break;
        case 'decimal':  inputNum('.');      break;
      }
    }
  });
});

/* ============================================================
   EVENT LISTENERS — Keyboard
   ============================================================ */

document.addEventListener('keydown', e => {
  // Ignore modifier combos (Ctrl+C, etc.)
  if (e.ctrlKey || e.metaKey || e.altKey) return;

  let selector = null;

  if      (e.key >= '0' && e.key <= '9')        { inputNum(e.key);  selector = `[data-num="${e.key}"]`; }
  else if (e.key === '.')                        { inputNum('.');    selector = `[data-action="decimal"]`; }
  else if (e.key === '+')                        { inputOp('+');     selector = `[data-op="+"]`; }
  else if (e.key === '-')                        { inputOp('-');     selector = `[data-op="-"]`; }
  else if (e.key === '*')                        { inputOp('*');     selector = `[data-op="*"]`; }
  else if (e.key === '/')    { e.preventDefault(); inputOp('/');     selector = `[data-op="/"]`; }
  else if (e.key === 'Enter' || e.key === '=')   { compute();        selector = `[data-action="equals"]`; }
  else if (e.key === 'Escape')                   { clear();          selector = `[data-action="clear"]`; }
  else if (e.key === 'Backspace')                { backspace();      selector = `[data-action="backspace"]`; }
  else if (e.key === '%')                        { percent();        selector = `[data-action="percent"]`; }

  // Visual feedback on the matching button
  if (selector) {
    const btn = document.querySelector(selector);
    if (btn) {
      btn.classList.add('pressed');
      ripple(btn, null);
      btn.addEventListener('animationend', () => btn.classList.remove('pressed'), { once: true });
    }
  }
});