var ACE = document.getElementById.bind(document);
var dom = require("ace/lib/dom");
// add command to all new editor instaces
// fullscreen
require("ace/commands/default_commands").commands.push({
	name : "Toggle Fullscreen",
	bindKey : "F11",
	exec : function(editor) {
		dom.toggleCssClass(document.body, "fullScreen");
		dom.toggleCssClass(editor.container, "fullScreen");
		editor.setAutoScrollEditorIntoView();
		editor.resize();
	},
	readOnly : true
});

var themes = {
	bright : [ "chrome", "clouds", "crimson_editor", "dawn", "dreamweaver",
			"eclipse", "github", "solarized_light", "textmate", "tomorrow" ],
	dark : [ "clouds_midnight", "cobalt", "idle_fingers", "kr_theme",
			"merbivore", "merbivore_soft", "mono_industrial", "monokai",
			"pastel_on_dark", "solarized_dark", "terminal", "tomorrow_night",
			"tomorrow_night_blue", "tomorrow_night_bright",
			"tomorrow_night_eighties", "twilight", "vibrant_ink" ]};
themes = [].concat(themes.bright, themes.dark);
var editor;
function initAceEditor() {
	
		// create editor
		editor = ace.edit("editor");
		editor.setTheme("ace/theme/twilight");
		editor.session.setMode("ace/mode/groovy");
		
		editor.setAutoScrollEditorIntoView();
		document.getElementById('editor').style.fontSize = '14px';
		editor.getSession().on('change', function(e) {
			$('.editor-textarea').val(editor.getValue());
		});
}

initAceEditor();

function finselection() {
	VariablesDlg.hide();
	var c = editor.selection.getCursor();
	editor.gotoLine(c.row, c.column, true);
	editor.navigateTo(c.row, c.column);
	editor.focus();
}