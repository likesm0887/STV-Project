package adapter;

import java.util.Optional;

public class Instruction {
    private Optional<String> activity;
    private String event;
    private Optional<String> element;
    private Optional<String> eventParameter;
    private Optional<String> elementParameter;

    private Instruction(String activity, String event, String element, String eventParameter, String elementParameter) {
        this.activity = Optional.ofNullable(activity);
        this.event = event;
        this.element = Optional.ofNullable(element);
        this.eventParameter = Optional.ofNullable(eventParameter);
        this.elementParameter = Optional.ofNullable(elementParameter);
    }

    public static Instruction createUnaryInstruction(String event, String eventParameter) {
        return new Instruction(null, event, null, eventParameter, null);
    }

    public static Instruction createTernaryInstruction(String activity, String event, String element, String eventParameter, String elementParameter) {
        return new Instruction(activity, event, element, eventParameter, elementParameter);
    }

    public String getActivity() {
        return activity.orElse("");
    }

    public String getEvent() {
        return event;
    }

    public String getElement() {
        return element.orElse("");
    }

    public Optional<String> getEventParameter() {
        return eventParameter;
    }

    public Optional<String> getElementParameter() {
        return elementParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Instruction))
            return false;

        Instruction target = (Instruction)o;

        boolean result = this.event.equals(target.event) &&
                         this.activity.equals(target.activity) &&
                         this.element.equals(target.element) &&
                         this.eventParameter.equals(target.eventParameter) &&
                         this.elementParameter.equals(target.elementParameter);

        return result;
    }
}

