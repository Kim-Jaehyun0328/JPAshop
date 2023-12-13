package jpabook.jpashop.domain.item;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id @Getter
    @Column(name = "caregory_id")
    private Long id;

    private String name;

    @ManyToMany //매니 투 매니는 중간 테이블이 필요하다.
    @JoinTable(name = "category_item",
    joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메소드==//

    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
