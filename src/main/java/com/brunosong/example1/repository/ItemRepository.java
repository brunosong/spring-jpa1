package com.brunosong.example1.repository;

import com.brunosong.example1.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if( item.getId() == null ) {
            em.persist(item);
        } else {
            //준영속 상태에 객체를 영속상태의 객체로 변경해서 저장한다.
            //단순하게 설명하자면
            /*
                Item findItem = itemRepository.findOne(itemId); //영속상태가 된다.
                findItem.setPrice(book.getPrice());   //더티채킹이 되어서 커밋 시점에 업데이트를 한다.
                findItem.setName(book.getName());
                findItem.setStockQuantity(book.getStockQuantity());
                이거랑 같은거라고 생각하면 된다.
                다른것은
                return findItem; 이라고 생각하면 된다. 영속성 관리가 되는것이다.
                그리고 머지는 모든것을 다 바꾼다. 선택을 할수 있는것이 아니다. 필드가 빠져있다면 빠져있게 업데이트 된다.
                이런이유로 셋터는 쓰지 말자~~~~ 라고 한다.
            */
            // 하지만 차이는 존재한다.  Item merge = em.merge(item);  Item merge 이게 영속성 컨텍스트로 관리가 된다.
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }

}
