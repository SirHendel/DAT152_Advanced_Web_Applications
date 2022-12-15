export default class TaskList extends HTMLElement {
	#shadow;
	#cssfile = "tasklist.css";
	#tbodyElm = null;
	#addCallbacks = new Map();
	#addCallbackId = 0;
	#modifyCallbacks = new Map();
	#modifyCallbackId = 0;
	#deleteCallbacks = new Map();
	#deleteCallbackId = 0;
	#statuses = new Map();
	#statusesId = 0;

	constructor() {
		// Always call super first in constructor
		super();

		// Entry point to the shadow DOM
		this.#shadow = this.attachShadow({ mode: 'closed' });
		this.#createLink();
		this.#createHTML();
	}

	#createLink() {
		const link = document.createElement('link');

		const path = import.meta.url.match(/.*\//)[0];
		link.href = path.concat(this.#cssfile);
		link.rel = "stylesheet";
		link.type = "text/css";
		this.#shadow.appendChild(link);
	}

	#createHTML() {
		const wrapper = document.createElement('div');
		wrapper.id = "wrapper";
		const content = "<p data-list>Waiting for server data...</p>";

		const button = document.createElement("button");
		button.id = "btnNewTask";
		button.type = "button";
		button.textContent = "New task";
		button.disabled = true;
		button.addEventListener("click", () => { this.#addTask() });

		wrapper.insertAdjacentHTML('beforeend', content);
		wrapper.appendChild(button);
		this.#shadow.appendChild(wrapper);
	}

	addtaskCallback(method) {
		this.#addCallbacks.set(this.#addCallbackId, method);
		const prevId = this.#addCallbackId;
		++this.#addCallbackId;
		return prevId;
	}

	changestatusCallback(method) {
		this.#modifyCallbacks.set(this.#modifyCallbackId, method);
		const prevId = this.#modifyCallbackId;
		++this.#modifyCallbackId;
		return prevId;
	}

	deletetaskCallback(method) {
		this.#deleteCallbacks.set(this.#deleteCallbackId, method);
		const prevId = this.#deleteCallbackId;
		++this.#deleteCallbackId;
		return prevId;
	}

	enableaddtask() {
		const btn = this.#shadow.querySelector('button[id=btnNewTask]');
		btn.disabled = false;
	}

	setParagraphText(text) {
		const paragraph = this.#shadow.querySelector('p');
		paragraph.textContent = text;
	}

	noTask() {
		const paragraph = this.#shadow.querySelector('p');
		paragraph.textContent = 'No tasks were found.';

		const table = this.#shadow.querySelector('table');
		if (table != null) {
			table.remove();
		}
	}

	showTask(task) {
		this.#tbodyElm = this.#shadow.querySelector("tbody");
		if (this.#tbodyElm == null) {
			const wrapper = this.#shadow.getElementById("wrapper");
			const content = `
            <table>
            <thead><tr><th>Task</th><th>Status</th><th>Modify</th><th></th></tr></thead>
            <tbody></tbody>
            </table>
            `;
			wrapper.insertAdjacentHTML('beforeend', content);
			this.#tbodyElm = this.#shadow.querySelector("tbody");
		}
		const row = this.#tbodyElm.insertRow(0);
		row.setAttribute("data-taskid", task.id);

		for (let i = 0; i < 4; ++i) {
			row.insertCell(-1);
		}
		row.cells[0].textContent = task.title;
		row.cells[1].textContent = task.status;

		const select = document.createElement("select");
		select.addEventListener("change", () => {
			this.#changeTask(task.id, select.value);
		});

		this.#statuses.forEach(status => {
			const option = document.createElement("option");
			option.textContent = status;
			option.value = status;
			select.appendChild(option);
		});

		row.cells[2].appendChild(select);

		const button = document.createElement("button");
		button.type = "button";
		button.textContent = "Remove";
		button.addEventListener("click", () => { this.#deleteTask(task.id) });
		row.cells[3].appendChild(button);
	}

	updateTask(modify) {
		const row = this.#tbodyElm.querySelector(`tr[data-taskid="${modify.id}"]`);
		if (row != null) {
			row.cells[1].textContent = modify.status;
		}
	}

	removeTask(taskId) {
		const row = this.#tbodyElm.querySelector(`tr[data-taskid="${taskId}"]`);
		if (row != null) {
			this.#tbodyElm.removeChild(row);
		}

		if (this.#tbodyElm.rows.length == 0) {
			const wrapper = this.#shadow.getElementById("wrapper");
			wrapper.remove();
			this.#createHTML();
			this.enableaddtask();
		}
	}

	setStatuseslist(list) {
		for (var status in list) {
			this.#statuses.set(this.#statusesId, list[status]);
			++this.#statusesId;
		}
	}

	#addTask() {
		this.#addCallbacks.forEach(
			method => { method() }
		);
	}

	#changeTask(taskId, status) {
		const task = this.#getTaskFromHTML(taskId);
		if (window.confirm('Set task: ' + task.title + ' to ' + status + '?')) {
			this.#modifyCallbacks.forEach(
				method => { method(taskId, status) }
			);
		}
	}

	#deleteTask(taskId) {
		const task = this.#getTaskFromHTML(taskId);
		if (window.confirm('Delete task: ' + task.title + '?')) {
			this.#deleteCallbacks.forEach(
				method => { method(taskId) }
			);
		}
	}

	#getTaskFromHTML(taskId) {
		const row = this.#tbodyElm.querySelector(`tr[data-taskid="${taskId}"]`);
		const title = row.cells[0].textContent;
		const status = row.cells[1].textContent;
		return { title: title, status: status, id: taskId };
	}
}