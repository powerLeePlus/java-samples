use shiro_demo;


-- ----------------------------
-- Records of shiro_user
-- ----------------------------
BEGIN;
INSERT INTO `shiro_user` VALUES (1, 'admin', '24dce55acdcb3b6363c7eacd24e98cb7', '28qr0xu%');
INSERT INTO `shiro_user` VALUES (2, 'lwq', 'ca9f1c951ce2bfb5669f3723780487ff', 'IWd1)#or');
INSERT INTO `shiro_user` VALUES (3, 'test', 'ca9f1c951ce2bfb5669f3723780487ff', 'bljOhDqx');
COMMIT;

-- ----------------------------
-- Records of shiro_role
-- ----------------------------
BEGIN;
INSERT INTO `shiro_role` VALUES (1, 'admin');
INSERT INTO `shiro_role` VALUES (2, 'user');
INSERT INTO `shiro_role` VALUES (3, 'product');
COMMIT;

-- ----------------------------
-- Records of shiro_user_role
-- ----------------------------
BEGIN;
INSERT INTO `shiro_user_role` VALUES (1, 1, 1);
INSERT INTO `shiro_user_role` VALUES (2, 2, 2);
INSERT INTO `shiro_user_role` VALUES (3, 3, 3);
COMMIT;

-- ----------------------------
-- Records of shiro_perms
-- ----------------------------
BEGIN;
INSERT INTO `shiro_perms` VALUES (1, 'user:*:*', '');
INSERT INTO `shiro_perms` VALUES (2, 'product:*:01', NULL);
INSERT INTO `shiro_perms` VALUES (3, 'order:*:*', NULL);
COMMIT;

-- ----------------------------
-- Records of shiro_role_perms
-- ----------------------------
BEGIN;
INSERT INTO `shiro_role_perms` VALUES (1, 1, 1);
INSERT INTO `shiro_role_perms` VALUES (2, 1, 2);
INSERT INTO `shiro_role_perms` VALUES (3, 1, 3);
INSERT INTO `shiro_role_perms` VALUES (4, 2, 1);
INSERT INTO `shiro_role_perms` VALUES (5, 3, 2);
COMMIT;





