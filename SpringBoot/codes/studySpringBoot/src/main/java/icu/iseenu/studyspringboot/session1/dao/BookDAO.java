package icu.iseenu.studyspringboot.session1.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BookDAO {

    private String label = "1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookDAO{" +
                "label='" + label + '\'' +
                '}';
    }
}
