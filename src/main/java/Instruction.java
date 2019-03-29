public class Instruction {
    private String event ;
    private String activity;
    private String attribute;



    private String parameter = " ";
    public Instruction(String activity, String event, String attribute , String... parameter) {
        this.event = event;
        this.activity = activity;
        this.attribute = attribute;
        if (parameter.length != 0)
        {
            this.parameter = parameter[0];
        }
    }
    public String getParameter() {
        return parameter;
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


}

