export default class TaskView extends HTMLElement {
    #tasklist;
    #taskbox;
	
    constructor() {
        super();
        
        this.#tasklist = this.querySelector("task-list");
        this.#taskbox = this.querySelector("task-box");
        this.#setStatuses();
        this.#initTaskList();
        this.#initTaskBox();
    }
    
    #showTaskBoxCallback() {
        this.#taskbox.show();
    }
    
    #newTaskCallback(task) {
		this.#newtask(task)
		.then(data => {
			if(data != false && data.responseStatus) {
				this.#tasklist.showTask(data.task);
			} else {
				this.#tasklist.setParagraphText('Failed to save new task!');
			}
		});
		this.#taskbox.close();
	}
	
	#modifyCallback(taskId, status) {
		this.#modify(taskId, status)
		.then(data => {
			if(data != false && data.responseStatus) {
				this.#tasklist.updateTask(data);
			} else {
				this.#tasklist.setParagraphText('Failed to update task:' + taskId + ' with status: ' + status + '!');
			}
		});
	}
	
	#deleteCallback(taskId) {
		this.#deletetask(taskId)
		.then(data => {
			if(data != false && data.responseStatus) {
				this.#tasklist.removeTask(taskId);
			} else {
				this.#tasklist.setParagraphText('Failed to delete task:' + taskId + '!');
			}
		});
	}
    
    async #modify(id,status) {
        const url=`${config.servicesPath}/task/` + id
        try {
            const response = await fetch(url, {method: "PUT", headers: { "Content-Type": "application/json; charset=utf-8" },
                body: JSON.stringify({ 'status': status })});
            try {
                const result = await response.json();
                return result;
            } catch (error) {
                return false;
            }
        } catch (error) {
            return false;
        }
    }
    
    async #deletetask(id) {
        const url = `${config.servicesPath}/task/` + id
        try {
            const response = await fetch(url, { method: "DELETE" });
            try {
                const result = await response.json();
                return result;
            } catch (error) {
                return false;
            }
        } catch (error) {
            return false;
        }
    }
    
    async #newtask(task) {
        const url = `${config.servicesPath}/task`
        try {
            const response = await fetch(url, {method: "POST",headers: { "Content-Type": "application/json; charset=utf-8" },
            body: JSON.stringify(task)});
            try {
                const result = await response.json();
                return result;
            } catch (error) {
                return false;
            }
        } catch (error) {
            return false;
        }
    }
    
    async #getData() {
        const url=`${config.servicesPath}/tasklist`;
        try {
        const response = await fetch(url,{method:"GET"});
            try {
                const result = await response.json();
                return result;
                
            } catch (error) {
                return false;
            }
        } catch (error) {
            return false;
        }
    }
    
    async #getallstatuses() {
        const url=`${config.servicesPath}/allstatuses`;
        try {
        const response = await fetch(url,{method:"GET"});
            try {
                const result = await response.json();
                return result;
                
            } catch (error) {
                return false;
            }
        } catch (error) {
            return false;
        }
    }
    
    #setStatuses() {
		this.#getallstatuses()
        .then(data => {
			if(data != false) {
				if(data.responseStatus) {
                    this.#tasklist.setStatuseslist(data.allstatuses);
                    this.#taskbox.setStatuseslist(data.allstatuses);
				}
				else {
					this.#tasklist.setParagraphText('Server error, could not load statuses!');
				}
			}
			else {
				this.#tasklist.setParagraphText('Cannot connect to server!');
			}
		});
	}
	
	#initTaskList() {
		this.#getData()
		.then(data => {
			if(data != false) {
				if(data.responseStatus) {
					this.#tasklist.enableaddtask();
					this.#tasklist.addtaskCallback(this.#showTaskBoxCallback.bind(this));
					this.#tasklist.changestatusCallback(this.#modifyCallback.bind(this));
					this.#tasklist.deletetaskCallback(this.#deleteCallback.bind(this));
					
					for(var task in data.tasks) {
						this.#tasklist.showTask(data.tasks[task]);
					}
					
					if(data.tasks.length > 0) {
						this.#tasklist.setParagraphText('Found ' + data.tasks.length + ' items.');
					}
					else {
						this.#tasklist.setParagraphText('List is empty.');
					}
				}
				else {
					this.#tasklist.setParagraphText('Server error, could not load task list!');
				}
			}
			else {
				this.#tasklist.setParagraphText('Cannot connect to server!');
			}
		});
    }
    
    #initTaskBox() {
        this.#taskbox.newtaskCallback(this.#newTaskCallback.bind(this));
    }
}