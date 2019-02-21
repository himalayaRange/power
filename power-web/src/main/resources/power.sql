-- 存储过程修改定义者
UPDATE mysql.proc set DEFINER='root@%' WHERE NAME='P_DELETE_ROLE_REFENCE_INFO' AND db='power';

-- 删除角色关联信息表
DROP PROCEDURE if EXISTS P_DELETE_ROLE_REFENCE_INFO;
CREATE PROCEDURE P_DELETE_ROLE_REFENCE_INFO(IN paramter LONG)
BEGIN
   DECLARE tmp_role_menu_id LONG;
	-- 自定义控制游标循环变量
   DECLARE done INT DEFAULT FALSE;
	-- 根据角色ID查询角色菜单
   DECLARE cur_r_m CURSOR FOR SELECT id FROM t_sys_rolemenu where roleId = paramter;
   DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	 OPEN cur_r_m;
		myloop : LOOP
		FETCH cur_r_m INTO tmp_role_menu_id;
		-- 根据菜单ID查询菜单列
		-- DECLARE cur_r_m_c CURSOR FOR SELECT id , menuId, columnId FROM t_sys_rolemenucolumn where menuId = tmp_menuId;
		-- 根据菜单ID删除菜单列
		IF done THEN
		LEAVE myloop;
		END IF;
			DELETE FROM t_sys_rolemenucolumn where roleMenuId = tmp_role_menu_id;
  		    -- 交给spring管理，此处不提交
			--COMMIT;
		END LOOP myloop;
	CLOSE cur_r_m;
 -- 根据ID删除角色菜单
  DELETE FROM t_sys_rolemenu where roleId = paramter;
  DELETE FROM t_sys_role where id = paramter;
END;