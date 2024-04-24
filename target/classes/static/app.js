document.addEventListener('DOMContentLoaded', () => {
    //fetch all tasks when the dom is loaded
    fetchTasks();
    const form = document.querySelector('form');
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        const taskInput = form.querySelector('input[name="task"]');
        const task = taskInput.value.trim();
        //check if input is empty
        if (task) {
 
            taskInput.value = '';
            await addTask(task);
            fetchTasks();
        }
    });
    
});

//toggle the completion of a task
async function toggleLine(taskId) {
    const response = await fetch('/toggle', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `task=${encodeURIComponent(taskId)}`,
    });
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    fetchTasks();
}

//add a new task
async function addTask(task) {
    const response = await fetch('/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `task=${encodeURIComponent(task)}`,
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
}

//remove an existing task
async function deleteTask(task) {
    const response = await fetch('/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `task=${encodeURIComponent(task)}`,
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
}

//fetch the tasks and update the page
function fetchTasks() {
    fetch('/api/tasks')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const tasksList = document.querySelector('#tasksList');
            tasksList.innerHTML = '';
            //iterate over tasks to create a list
            data.forEach(task => {
                const li = document.createElement('li');
                li.textContent = task.TASK+ " ";
                //if clicked on call toggle to change completion status
                li.addEventListener('click', () => {
                    toggleLine(task.TASK);
                    fetchTasks(); 
                });
                //add to completed class if it is completed
                if (task.LINE === 1) {
                    li.classList.add('completed');
                }
                // create a delete button for each task
                tasksList.appendChild(li);
                const deleteButton = document.createElement('button');
                deleteButton.textContent = 'X';
                deleteButton.addEventListener('click', () => {
                    console.log(task.ID)
                    deleteTask(task.TASK);
                });
                li.appendChild(deleteButton);
                tasksList.appendChild(li);
            }); 
        })
        .catch(error => {
            console.error('Error fetching tasks:', error);
        });
}



