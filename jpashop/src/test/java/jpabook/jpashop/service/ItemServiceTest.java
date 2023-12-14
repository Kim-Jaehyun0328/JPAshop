package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ItemServiceTest {

    @Autowired ItemService itemService;


    @Test
    @Transactional
    public void 아이템_추가(){

        //given
        Item item = new Book();
        item.setName("itemA");
        item.setStockQuantity(30);

        //when
        itemService.saveItem(item);
        item.addStock(20);

        //then
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(50);
    }

    @Test
    @Transactional
    public void 아이템_재고_부족_예외(){
        //given
        Item item = new Book();
        item.setName("itemA");
        item.setStockQuantity(30);

        //when
        itemService.saveItem(item);

        try{
            item.removeStock(31);
        } catch(NotEnoughStockException e){
            return;
        }
        fail("재고가 부족합니다.");

        //then
    }
}