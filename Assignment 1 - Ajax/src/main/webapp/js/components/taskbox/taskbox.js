export default class TaskBox extends HTMLElement {
	#shadow;
	#cssfile = "taskbox.css";
	#dialog;
	#callbacks = new Map();
	#callbackId = 0;
	#statuses = new Map();
	#statusesId = 0;
	#firstCreation = true;

	constructor() {
		super();

		this.#shadow = this.attachShadow({ mode: 'closed' });
		this.#createLink();
	}

	newtaskCallback(method) {
		this.#callbacks.set(this.#callbackId, method);
		const prevId = this.#callbackId;
		++this.#callbackId;
		return prevId;
	}

	setStatuseslist(list) {
		for (var status in list) {
			this.#statuses.set(this.#statusesId, list[status]);
			++this.#statusesId;
		}
	}

	show() {
		if (this.#firstCreation) {
			this.#createHTML();
			this.#firstCreation = false;
		}
		this.#reset();
		this.#dialog.showModal();
	}

	close() {
		this.#dialog.close();
	}

	#reset() {
		this.#shadow.querySelector('#titleTextBox').value = "";
		this.#shadow.querySelector('#idSelectStatus').selectedIndex = 0;
	}

	#newTask() {
		const title = this.#shadow.querySelector('#titleTextBox').value;
		const status = this.#shadow.querySelector('#idSelectStatus').value;
		const task = { title: title, status: status };
		this.#callbacks.forEach(
			method => { method(task) }
		);
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
		const wrapper = document.createElement('dialog');
		this.#dialog = wrapper;

		const table = document.createElement("table");
		const tbody = document.createElement("tbody");
		const thead = document.createElement("thead"); // for close button

		// Create close button
		const closeButton = document.createElement("button");
		closeButton.id = "btnClose";
		closeButton.type = "button";
		closeButton.addEventListener("click", () => { this.close() });
		closeButton.innerHTML = "<img src=\"./img/close.png\">";
		const headRow = thead.insertRow(0);
		headRow.insertCell(0);
		headRow.insertCell(1);
		headRow.cells[1].appendChild(closeButton);

		// Create input row
		const titleTextBox = document.createElement("input");
		titleTextBox.id = "titleTextBox";
		titleTextBox.type = "text";

		// Create titleRow
		const titleRow = tbody.insertRow(0);
		titleRow.insertCell(0)
		titleRow.insertCell(1);
		titleRow.cells[0].textContent = "Title";
		titleRow.cells[1].appendChild(titleTextBox);

		// Create status selector
		const select = document.createElement("select");
		select.id = "idSelectStatus";
		this.#statuses.forEach(status => {
			const option = document.createElement("option");
			option.textContent = status;
			option.value = status;
			select.appendChild(option);
		});

		// Create status row
		const statusRow = tbody.insertRow(1);
		statusRow.insertCell(0)
		statusRow.insertCell(1);
		statusRow.cells[0].textContent = "Status";
		statusRow.cells[1].appendChild(select);

		// Create button
		const addTaskButton = document.createElement("button");
		addTaskButton.id = "btnAddTask";
		addTaskButton.type = "button";
		addTaskButton.textContent = "Add task";
		addTaskButton.addEventListener("click", () => { this.#newTask() });

		table.appendChild(thead);
		table.appendChild(tbody);
		wrapper.append(table);
		wrapper.appendChild(addTaskButton);
		this.#shadow.appendChild(wrapper);
	}
}