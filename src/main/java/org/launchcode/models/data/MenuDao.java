package org.launchcode.models.data;

import org.launchcode.models.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by there on 7/21/2017.
 */

@Repository
@Transactional
public interface MenuDao extends CrudRepository<Menu, Integer> {
}
