package adapter;

import java.util.Optional;

public class Instruction {
    private String event ;
    private String activity;
    private String attribute;
    private Optional<String> eventParameter;
    private Optional<String> elementParameter;

    public Instruction(String activity, String event, String attribute , Optional<String> eventParameter , Optional<String> elementParameter) {
        this.event = event;
        this.activity = activity;
        this.attribute = attribute;
        this.eventParameter = !eventParameter.isPresent() ? Optional.empty() : Optional.of( eventParameter.get());
        this.elementParameter = !elementParameter.isPresent() ? Optional.empty() : Optional.of( elementParameter.get());
    }

    public String getEvent() {
        return event;
    }

    public String getActivity() {
        return activity;
    }

    public String getAttribute() {
        return attribute;
    }

    public Optional<String> getEventParameter() {
        return eventParameter;
    }

    public Optional<String> getElementParameter() {
        return elementParameter;
    }

    @Override
    public String toString() {
        String result = "Event: " + event;
        result += "\nActivity: " + activity;
        result += "\nAttribute" + attribute;
        result += "\nEventParameter" + eventParameter;
        result += "\nelementParameter" + elementParameter;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Instruction))
            return false;

        Instruction target = (Instruction)o;

        boolean result = this.event.equals(target.event) &&
                         this.activity.equals(target.activity) &&
                         this.attribute.equals(target.attribute) &&
                         this.eventParameter.equals(target.eventParameter) &&
                         this.elementParameter.equals(target.elementParameter);

        return result;
    }
}

