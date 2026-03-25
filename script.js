const output = document.getElementById('game-output');
const input = document.getElementById('game-input');
const button = document.getElementById('submit-command');

function log(text) {
  output.innerHTML += `<p>${text}</p>`;
  output.scrollTop = output.scrollHeight;
}

function processCommand(cmd) {
  cmd = cmd.trim().toLowerCase();
  if (!cmd) return;
  if (cmd === 'look') return 'You are in an empty room.';
  if (cmd === 'help') return 'Commands: look, help, reset';
  if (cmd === 'reset') return 'Game reset.';
  return `Unknown command: "${cmd}"`;
}

button.addEventListener('click', () => {
  const cmd = input.value;
  if (!cmd) return;
  log(`> ${cmd}`);
  log(processCommand(cmd));
  input.value = '';
  input.focus();
});

input.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') button.click();
});

log('Welcome to the text adventure. Type "help".');