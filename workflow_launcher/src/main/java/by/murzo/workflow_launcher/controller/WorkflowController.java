package by.murzo.workflow_launcher.controller;

import by.murzo.workflow_launcher.service.WorkflowLauncher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/launch", produces = "application/json")
public class WorkflowController {

    private final WorkflowLauncher workflowLauncher;
    @Autowired
    public WorkflowController(WorkflowLauncher workflowLauncher) {
        this.workflowLauncher = workflowLauncher;
    }

    @PostMapping("/{filename}")
    public void launchWorkflow(@PathVariable("filename") String filename) {
        workflowLauncher.launchWorkflow(filename);

    }


}
