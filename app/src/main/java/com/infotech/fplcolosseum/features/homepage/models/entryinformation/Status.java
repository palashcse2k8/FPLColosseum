package com.infotech.fplcolosseum.features.homepage.models.entryinformation;

public class Status {
    private String qualification_event = null;
    private String qualification_numbers = null;
    private String qualification_rank = null;
    private String qualification_state = null;


    // Getter Methods

    public String getQualification_event() {
        return qualification_event;
    }

    public String getQualification_numbers() {
        return qualification_numbers;
    }

    public String getQualification_rank() {
        return qualification_rank;
    }

    public String getQualification_state() {
        return qualification_state;
    }

    // Setter Methods

    public void setQualification_event(String qualification_event) {
        this.qualification_event = qualification_event;
    }

    public void setQualification_numbers(String qualification_numbers) {
        this.qualification_numbers = qualification_numbers;
    }

    public void setQualification_rank(String qualification_rank) {
        this.qualification_rank = qualification_rank;
    }

    public void setQualification_state(String qualification_state) {
        this.qualification_state = qualification_state;
    }
}
