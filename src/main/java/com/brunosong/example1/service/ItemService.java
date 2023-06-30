package com.brunosong.example1.service;

import com.brunosong.example1.domain.item.Book;
import com.brunosong.example1.domain.item.Item;
import com.brunosong.example1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }


    /* 변경감지 기능 사용 */
    @Transactional
    public void updateItem(Long itemId, Book book) {

        Item findItem = itemRepository.findOne(itemId); //영속상태가 된다.
        //실제로는 셋을 쓰면 안되기때문에 셋을 쓰지 않고 findItem.update(... ) 이런식으로 메서드를 만들어서 처리를 해야 한다.
        //안그러면 다른곳에서 변경이 될때 알아차리기가 쉽지 않다.

        findItem.setPrice(book.getPrice());   //더티채킹이 되어서 커밋 시점에 업데이트를 한다.
        findItem.setName(book.getName());
        findItem.setStockQuantity(book.getStockQuantity());

    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
