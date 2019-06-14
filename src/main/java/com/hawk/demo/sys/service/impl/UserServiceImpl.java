package com.hawk.demo.sys.service.impl;

import com.hawk.demo.sys.dao.DeptDao;
import com.hawk.demo.sys.dao.UserDao;
import com.hawk.demo.sys.dao.UserRoleDao;
import com.hawk.demo.sys.model.DeptDO;
import com.hawk.demo.sys.model.UserDO;
import com.hawk.demo.sys.model.UserRoleDO;
import com.hawk.demo.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.persistence.criteria.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2019-06-12.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private DeptDao deptDao;
    @Override
    public Example<UserDO> listExample(Map<String, Object> params, Class<UserDO> beanClass) throws Exception {
        return null;
    }

    @Override
    public Sort listSort(Map<String, Object> params, String sortField) {


        return null;
    }

    @Override
    public Pageable listPageable(Map<String, Object> params, Sort sort) {
        return null;
    }

    @Override
    public Pageable listPageable(Map<String, Object> params, String sortField) {
        return null;
    }

    @Override
    public List<UserDO> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<UserDO> findAllById(List<Long> ids) {
        return null;
    }

    @Override
    public List<UserDO> findAll(Specification<UserDO> spec) {
        return null;
    }

    @Override
    public List<UserDO> findAll(Example<UserDO> example) {
        return null;
    }

    @Override
    public List<UserDO> findAll(Map<String, Object> params, Class<UserDO> beanClass) throws Exception {
        return null;
    }

    @Override
    public List<UserDO> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<UserDO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserDO> findAll(Specification<UserDO> spec, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserDO> findAll(Example<UserDO> example, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserDO> findAll(Map<String, Object> params, Class<UserDO> beanClass, String sortField) throws Exception {
        return null;
    }

    @Override
    public List<UserDO> findAll(Specification<UserDO> spec, Sort sort) {
        return null;
    }

    @Override
    public List<UserDO> findAll(Example<UserDO> example, Sort sort) {
        return null;
    }

    @Override
    public long count(Specification<UserDO> spec) {
        return 0;
    }

    @Override
    public long count(Example<UserDO> example) {
        return 0;
    }

    /**
     * 分页查询用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<UserDO> find(int pageNum, int pageSize) {
        return userDao.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "userId"));
    }

    /**
     * 根据ID来查询用户
     * @param id
     * @return
     */
    @Override
    public UserDO findById(Long id) {
       List<Long> roleIds= userRoleDao.listRoleIdByUserId(id);
        UserDO user=userDao.getOne(id);
        user.setRoleIds(roleIds);

        return user;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public UserDO findOne(Specification<UserDO> spec) {
        return null;
    }

    @Override
    public UserDO findOne(Example<UserDO> example) {
        return null;
    }

    @Override
    public void deleteAll(List<UserDO> list) {

        userDao.deleteAll(list);

    }

    /**
     * 根据ID来删除用户
     * @param id
     */
    @Override
    public void deleteById(Long id) {

        userRoleDao.deleteByUserId(id);
        userDao.deleteById(id);

    }
    /*

     */
    @Override
    public void deleteByIds(Long... ids) {


    }

    /*
    删除用户
     */
    @Override
    public void delete(UserDO userDO) {

    }

    /*
     添加和更新用户
     */
    @Override
    public UserDO save(UserDO userDO) {

        UserDO user=userDao.save(userDO);
        Long userId=userDO.getUserId();
        List<Long> roleIds=userDO.getRoleIds();
        //通过用户id删除以前得用户角色记录
        userRoleDao.deleteByUserId(userId);
      List<UserRoleDO> userRoleDOList= new ArrayList<>();
if(roleIds!=null)
{  for(Long roleId:roleIds)
        {
            UserRoleDO userRoleDO=new UserRoleDO();
            userRoleDO.setUserId(userId);
            userRoleDO.setRoleId(roleId);
            userRoleDOList.add(userRoleDO);

        }}
       if(userRoleDOList.size()>0) {
           userRoleDao.saveAll(userRoleDOList);
       }
        return user;
    }

    /*

     */
    @Override
    public List<UserDO> saveAll(List<UserDO> list) {
        return null;
    }

    @Override
    public List<UserDO> listUserByDepId(Long depId) {

        DeptDO deptDO=deptDao.getOne(depId);


        List<UserDO> userDOPage=userDao.findUserDOByDeptId(depId);

        for(UserDO user:userDOPage)
        {
            user.setDeptName(deptDO.getName());
        }
        return userDOPage;
    }

    @Override
    public List<UserDO> queryUser(String userName, String address, String mobile) {

               Specification<UserDO> specification=new Specification<UserDO>() {
                   @Override
                   public Predicate toPredicate(Root<UserDO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                       Expression nameCol=root.get("name");
                       Expression addressCol=root.get("liveAddress");
                       Expression mobileCol=root.get("mobile");

                       List<Predicate> predicateList=new ArrayList<>();

                       if(userName!=null)
                       {
                           predicateList.add(criteriaBuilder.like(nameCol,"%"+userName+"%"));
                        //   criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.like(nameCol,userName)));
                       }if(address!=null)
                       {
                           predicateList.add(criteriaBuilder.like(addressCol,"%"+address+"%"));
                           //criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.like(addressCol,address)));
                       }if(mobile!=null)
                       {
                           predicateList.add(criteriaBuilder.like(mobileCol,"%"+mobile+"%"));
                         //  criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.like(mobileCol,mobile)));
                       }
                       criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));

                       return null;
                   }
               };

        return userDao.findAll(specification);
    }
}
