package cy.myapp.controller;

import cy.myapp.model.Pledge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class pledgeController {
    private List<Pledge> pledges = new ArrayList<> ();
    private AtomicLong nextid = new AtomicLong();

    @GetMapping("/hello")
    public String getHelloMessage() {
        return "Hello Wolrld!! From Spring";
    }

    @PostMapping("/pledges")
    @ResponseStatus(HttpStatus.CREATED)
    public Pledge createNewPledge(@RequestBody Pledge pledge) {
        pledge.setId(nextid.incrementAndGet());
        pledges.add(pledge);
        return pledge;
    }

    @GetMapping("/pledges")
    public List<Pledge> getAllPledges() {
        pledges.add(new Pledge(10, "Akshay", "I am a Coder"));
        pledges.add(new Pledge(15, "Shivam", "He is a developer"));
        return pledges;
    }

    @GetMapping("/pledges/{id}")
    public Pledge getOnePledge(@PathVariable("id") long pledgeId) {
        for(Pledge pledge : pledges) {
            if(pledge.getId() == pledgeId) {
                return pledge;
            }
        }
        throw new IllegalArgumentException();
    }

    @PostMapping("/pledges/{id}")
    public Pledge editOnePledge(@PathVariable("id") long pledgeId, @RequestBody Pledge newPledge) {
        for(Pledge pledge : pledges) {
            if(pledge.getId() == pledgeId) {
                pledges.remove(pledge);
                newPledge.setId(pledgeId);
                pledges.add(newPledge);
                return newPledge;
            }
        }
        throw new IllegalArgumentException();
    }

    //Create Exception Handling
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {
        // Nothing To do
    }

}
