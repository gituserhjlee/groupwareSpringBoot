package myproject.groupware;

import lombok.Data;

import javax.persistence.*;

@Entity
@SequenceGenerator(name="board_seq_gen", sequenceName ="board_seq", initialValue = 1,allocationSize = 1)
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_gen")
    private Long num;

    private String title;
    private String writer;
}
