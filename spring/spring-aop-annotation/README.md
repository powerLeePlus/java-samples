# 基于spring aop和自定义注解实现的自动获取方法参数，并给参数赋值的示例

## 业务说明
### 现有组织架构：部门->小组->成员
- 有多个部门
- 一个部门下有多个小组
- 一个小组下有多个成员
### 需要根据当前登录用户获取当前用户可以查看的部门，或者小组，或者成员列表，
如：
#### (一)部门负责人能看的部门、小组、成员列表：
1. 可以查看自己作为部门负责人的部门列表；
2. 可以查看自己部门下所有小组列表；
3. 可以查看自己部门下所有小组下所有成员列表；
#### (二)组长能看的部门、小组、成员列表：
1. 可以查看自己组所在的部门列表；
2. 可以查看自己作为组长的所有小组列表；
3. 可以查看自己作为组长的所有小组下所有成员列表；
#### (三)成员能看的部门、小组、成员列表：
1. 可以查看自己所在组所在的部门列表；
2. 可以查看自己所在组列表；
3. 可以查看自己；

### 正常做法是什么呢
每次业务方法需要查询的时候，
1. 获取当前登录用户
2. 通过登录用户查询出有权查看的列表
3. 使用列表

### 优化分析
- 每次都要做上面两部重复的操作。不仅流程重复，代码也重复
- 所以，如果将这两部分封装起来直接使用就会减少代码冗余，提高效率
- 此时可以封装成一个方法，每次调用即可。
- 优化到这一步还有没有优化的空间呢？当然有，
- 如果我们直接使用这个列表呢？不需要每次都调用封装的方法。

### 优化方案
1. 我们在方法上标注一个注解`@PermissionOrgAccessor`表示要给这个方法自动注入参数
2. 在方法上定义一个要使用的列表参数(如List<DepNameDto> depNameDtos)，并用一个注解`@PermissionOrg`标注
3. 使用aop拦截方法，并在增强(环绕方法)中查出列表数据(通过获取登录用户，将其传参)，并赋值给该参数，并调用目标方法
4. 调用该方法时该参数直接指定`null`即可
5. 在方法中就可以直接使用该列表了。而不需要关注该列表的查询，因为已经在aop拦截增强中自动注入了值

### 进一步优化（自动赋值的参数依赖其他参数）
如果自动赋值的参数依赖其他参数，如：查询小组列表时，除了登录用户信息，还依赖部门ID，部门ID作为参数传递。

此时面临问题是需要在AOP拦截中获取这个部门ID，实现方式
1. `@PermissionOrg`注解中增加属性用以标识依赖的参数在方法中的位置(index)
2. 在aop增强中，通过该index获取依赖参数的值，
3. 这样就可以正常传递该参数查询了


