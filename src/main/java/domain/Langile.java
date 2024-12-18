package domain;

import javax.persistence.Entity;

@Entity
public class Langile extends Pertsona{

    public Langile() {super();}
    public Langile(String userName, String password) {
        super(userName, password);
    }
}
