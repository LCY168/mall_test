
//初始化
$(document).ready(function () {
    //侧边栏
    const navItems = document.querySelectorAll('.nav-link');
    const pages = document.querySelectorAll('.page');
    pages.forEach(function (item , index) {
        if (index == 0){
            item.style.display = 'block';
        }else {
            item.style.display = 'none';
        }
    });

    navItems.forEach(function (item , index) {
        item.addEventListener('click',function () {
            navItems.forEach(function (item , index) {
                item.classList.remove('active');
                pages[index].style.display = 'none';
            })
            item.classList.add('active')
            pages[index].style.display = 'block';
        })
    });

    //登录用户
    getLoginUser();
    //用户管理
    listUser();
    //分类管理
    listClassfyCategory();
    //商品管理
    listProduct();
    //订单管理
    listCart();
    //轮播图管理
    listImg();
    //listCategory();


    //用户管理Modal
    const itemUserModal = document.querySelector('#itemUserModal');
    if (itemUserModal) {
        itemUserModal.addEventListener("show.bs.modal",function (e) {
            const button = e.relatedTarget;
            const type = button.getAttribute('data-bs-type');

            const modalTitle = $('#itemUserModalLabel');
            const submitBtn = $('#userSubmit');

            if (type === 'add'){
                modalTitle.text('添加用户');
                submitBtn.text('添加');
                submitBtn.off('click').on('click',addProduct);
                clearModal();
            }else if (type === 'edit'){
                modalTitle.text('编辑用户');
                submitBtn.text('保存');
                const id = button.getAttribute('data-bs-user-id');
                submitBtn.off('click').on('click',{id:id},editUser);
                clearUserModal();
                getUser(id);
            }
        })
    }

    //商品分类管理modal
    const itemCategoryModal = document.querySelector('#itemCategoryModal');
    if (itemCategoryModal) {
        itemCategoryModal.addEventListener("show.bs.modal",function (e) {
            const button = e.relatedTarget;
            const type = button.getAttribute('data-bs-type');

            const modalTitle = $('#itemCategoryModalLabel');
            const submitBtn = $('#cateSubmit');

            if (type === 'add'){
                modalTitle.text('添加分类');
                submitBtn.text('添加');
                submitBtn.off('click').on('click', addCategory);
                clearCategoryModal();
            }else if (type === 'edit'){
                modalTitle.text('编辑分类');
                submitBtn.text('保存');
                const id = button.getAttribute('data-bs-cate-id');
                submitBtn.off('click').on('click',{id:id},editCategory);
                clearCategoryModal();
                getCategory(id);
            }
        })
    }

    //商品管理Modal
    const itemModal = document.querySelector('#itemModal');
    if (itemModal) {
        itemModal.addEventListener("show.bs.modal",function (e) {
            const button = e.relatedTarget;
            const type = button.getAttribute('data-bs-type');

            const modalTitle = $('#itemModalLabel');
            const submitBtn = $('#submit');

            if (type === 'add'){
                modalTitle.text('添加商品');
                submitBtn.text('添加');
                submitBtn.off('click').on('click',addProduct);
                clearModal();
            }else if (type === 'edit'){
                modalTitle.text('编辑商品');
                submitBtn.text('保存');
                const id = button.getAttribute('data-bs-prod-id');
                submitBtn.off('click').on('click',{id:id},editProduct);
                clearModal();
                getProduct(id);
            }
        })
    }

    //订单管理Modal
    const itemCartModal = document.querySelector('#itemCartModal');
    if (itemCartModal) {
        itemCartModal.addEventListener("show.bs.modal",function (e) {
            const button = e.relatedTarget;
            const type = button.getAttribute('data-bs-type');

            const modalTitle = $('#itemCartModalLabel');
            const submitBtn = $('#cartSubmit');

            if (type === 'add'){
                modalTitle.text('添加订单');
                submitBtn.text('添加');
                submitBtn.off('click').on('click',addCart);
                clearCartModal();
            }else if (type === 'edit'){
                modalTitle.text('编辑订单');
                submitBtn.text('保存');
                const id = button.getAttribute('data-bs-cart-id');
                submitBtn.off('click').on('click',{id:id},editCart);
                clearCartModal();
                getCart(id);
            }
        })
    }
    //轮播图管理Modal
    const itemImgModal = document.querySelector('#itemImgModal');
    if (itemImgModal) {
        itemImgModal.addEventListener("show.bs.modal",function (e) {
            const button = e.relatedTarget;
            const type = button.getAttribute('data-bs-type');

            const modalTitle = $('#itemImgModalLabel');
            const submitBtn = $('#imgSubmit');

            if (type === 'add'){
                modalTitle.text('添加轮播图');
                submitBtn.text('添加');
                submitBtn.off('click').on('click',addImg);
                clearCartModal();
            }else if (type === 'edit'){
                modalTitle.text('编辑轮播图');
                submitBtn.text('保存');
                const id = button.getAttribute('data-bs-cart-id');
                submitBtn.off('click').on('click',{id:id},editImg);
                clearImgModal();
                getImg(id);
            }
        })
    }
});




//用户管理
function listUser() {
    $.ajax({
        url: '/user/admin/list',
        type: 'get',
        dataType: 'json',
        success: function(res) {
            console.log(res);

            // 清空表格的 tbody 部分
            $('#userMange tbody').empty(); // 注意：这里应该是 goodsManage 而不是 goodsMange，确保选择器正确

            // 遍历数据并插入新的行
            res.data.forEach(user => {
                const row = `
	                <tr>
	                    <th scope="row">${user.id}</th>
	                    <td>${user.phone}</td>
	                    <td>${user.nickName}</td>	  
	                    <td>                 
	                        <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#itemUserModal" data-bs-type="edit" data-bs-user-id="${user.id}">编辑</button>
	                        <button type="button" class="btn btn-sm btn-danger" onclick="delUser(${user.id})">删除</button>
	                    </td>
	                </tr>`;
                $('#userMange tbody').append(row);
            });
        },
        error: function(xhr, status, error) {
            // 可选：处理 AJAX 请求失败的情况
            console.error("AJAX Error: " + status + ", " + error);
        }
    });
}

//编辑用户
function editUser(event){
    const id = event.data.id;
    const user = {
        id: id,
        phone: $('#userPhone').val(),
        nickName: $('#userNickName').val(),
    };
    $.ajax({
        url:'/user/admin/update',
        type:'post',
        dataType:'json',
        contentType: 'application/json; charset=utf-8',
        data:JSON.stringify(user),
        success:function (res) {
            console.log(res);
            if (res.code === 200) {
                alert('修改成功');
                $('#itemUserModal').modal('hide');
                $('#userMange tbody').empty();
                listUser();
            } else {
                alert('修改失败');
            }
        }
    });
}
//删除用户
function delUser(id) {
    $.ajax({
        url:'/user/admin/delete',
        type:'post',
        dataType:'json',
        contentType:'application/json; charset=utf-8',
        data:JSON.stringify([id]),
        success:function (res) {
            console.log(res);
            if (res.code === 200){
                alert('删除成功');
                $('#userMange tbody').empty();
                listUser();
            }else {
                alert('删除失败');
            }
        }
    });
}

function getUser(id) {
    $.ajax({
        url: '/user/admin/'+ id,
        type: 'get',
        dataType: 'json',
        success:function (res) {
            console.log(res);
            //res.data渲染为modal初始值
            const user = res.data;
            $('#userId').val(user.id);
            $('#userPhone').val(user.phone);
            $('#userNickName').val(user.nickName);
        }
    });
}







//类别管理
function listClassfyCategory() {
    $.ajax({
        url: '/category/list',
        type: 'get',
        dataType: 'json',
        success: function(res) {
            console.log(res);

            // 清空表格的 tbody 部分
            $('#goodsClassfyManage tbody').empty(); // 注意：这里应该是 goodsManage 而不是 goodsMange，确保选择器正确

            // 遍历数据并插入新的行
            res.data.forEach(cate => {
                const row = `
	                <tr>
	                    <th scope="row"></th>
	                    <th scope="row">${cate.id}</th>
	                    <td>${cate.code}</td>
	                    <td>${cate.name}</td>	  
	                    <td>                 
	                        <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#itemCategoryModal" data-bs-type="edit" data-bs-cate-id="${cate.id}">编辑</button>
	                        <button type="button" class="btn btn-sm btn-danger" onclick="delCategory(${cate.id})">删除</button>
	                    </td>
	                </tr>`;
                $('#goodsClassfyManage tbody').append(row);
            });
        },
        error: function(xhr, status, error) {
            // 可选：处理 AJAX 请求失败的情况
            console.error("AJAX Error: " + status + ", " + error);
        }
    });
}

//增加分类
function addCategory() {
    const category = {
        code: $('#cateCode').val(),
        name: $('#cateName').val(),
    };
    $.ajax({
        url: '/category/admin/insert',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(category),
        success: function (res) {
            console.log(res);
            if (res.code === 200) {
                alert('添加成功');
                $('#itemCategoryModal').modal('hide');
                $('#goodsClassfyManage tbody').empty();
                listClassfyCategory();
            } else {
                alert('添加失败');
            }
        }
    });
}

//编辑分类
function editCategory(event){
    const id = event.data.id;
    const cate = {
        id: id,
        code: $('#cateCode').val(),
        name: $('#cateName').val(),
    };
    $.ajax({
        url:'/category/admin/update',
        type:'post',
        dataType:'json',
        contentType: 'application/json; charset=utf-8',
        data:JSON.stringify(cate),
        success:function (res) {
            console.log(res);
            if (res.code === 200) {
                alert('修改成功');
                $('#itemCategoryModal').modal('hide');
                $('#goodsClassfyManage tbody').empty();
                listClassfyCategory();
            } else {
                alert('修改失败');
            }
        }
    });
}

//删除分类
function delCategory(id) {
    $.ajax({
        url:'/category/admin/delete',
        type:'post',
        dataType:'json',
        contentType:'application/json; charset=utf-8',
        data:JSON.stringify([id]),
        success:function (res) {
            console.log(res);
            if (res.code === 200){
                alert('删除成功');
                $('#goodsClassfyManage tbody').empty();
                listClassfyCategory();
            }else {
                alert('删除失败');
            }
        }
    });
}

function getCategory(id) {
    $.ajax({
        url: '/category/admin/'+ id,
        type: 'get',
        dataType: 'json',
        success:function (res) {
            console.log(res);
            //res.data渲染为modal初始值
            const cate = res.data;
            $('#cateId').val(cate.id);
            $('#cateCode').val(cate.code);
            $('#cateName').val(cate.name);
        }
    });
}




function upLoadFile(){
    const file=$('#imgUpload')[0].files[0];
    const formData=new FormData();
    formData.append('file',file);
    $.ajax({
        url:'common/uploadfile',
        type:'post',
        data:formData,
        contentType:false,
        processData: false,
        success:function (res){
            $('#imgPreview').attr('src',res.data);
            $('#img').val(res.data);
        }
    })
}

//商品管理
function listProduct() {
    $.ajax({
        url: '/product/list',
        type: 'get',
        dataType: 'json',
        success: function(res) {
            console.log(res);

            // 清空表格的 tbody 部分
            $('#goodsManage tbody').empty();

            // 遍历数据并插入新的行
            res.data.forEach(prod => {
                const row = `
	                <tr>
	                    <th scope="row">${prod.id}</th>
	                    <td>${prod.title}</td>
	                    <td>${prod.price}</td>
	                    <td>${prod.stocks}</td>
	                    <td>${prod.description}</td>
	                    <td>${prod.categoryName}</td>
	                    <td>
	                        <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#itemModal" data-bs-type="edit" data-bs-prod-id="${prod.id}">编辑</button>
	                        <button type="button" class="btn btn-sm btn-danger" onclick="delProduct(${prod.id})">删除</button>
	                    </td>
	                </tr>`;
                $('#goodsManage tbody').append(row);
            });
        },
        error: function(xhr, status, error) {
            // 可选：处理 AJAX 请求失败的情况
            console.error("AJAX Error: " + status + ", " + error);
            // 可以在这里添加一些错误处理的逻辑，比如显示错误消息给用户
        }
    });
}

function getProduct(id) {
    $.ajax({
        url: '/product/admin/'+ id,
        type: 'get',
        dataType: 'json',
        success:function (res) {
            console.log(res);
            //res.data渲染为modal初始值
            const prod = res.data;
            $('#title').val(prod.title);
            $('#code').val(prod.code);
            $('#price').val(prod.price);
            $('#stocks').val(prod.stocks);
            $('#description').val(prod.description);
            $('#categoryId').val(prod.categoryId);
            $('#imgPreview').attr('src',prod.img);
        }
    });
}


function addProduct() {
    const product = {
        title: $('#title').val(),
        code: $('#code').val(),
        price: $('#price').val(),
        stocks: $('#stocks').val(),
        description: $('#description').val(),
        categoryId: $('#categoryId').val(),
        img: $('#img').val()
    };
    $.ajax({
        url: '/product/admin/insert',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(product),
        success: function (res) {
            console.log(res);
            if (res.code === 200) {
                alert('添加成功');
                $('#itemModal ').modal('hide');
                $('#productManage tbody').empty();
                listProduct();
            } else {
                alert('添加失败');
            }
        }
    });
}

function editProduct(event){
    const id = event.data.id;
    const product = {
        id: id,
        title: $('#title').val(),
        code: $('#code').val(),
        price: $('#price').val(),
        stocks: $('#stocks').val(),
        description: $('#description').val(),
        categoryId: $('#categoryId').val(),
        img: $('#img').val()
    };
    $.ajax({
        url:'/product/admin/update',
        type:'post',
        dataType:'json',
        contentType: 'application/json; charset=utf-8',
        data:JSON.stringify(product),
        success:function (res) {
            console.log(res);
            if (res.code === 200) {
                alert('修改成功');
                $('#itemModal').modal('hide');
                $('#productManage tbody').empty();
                listProduct();
            } else {
                alert('修改失败');
            }
        }
    });
}

function delProduct(id) {
    $.ajax({
        url:'/product/admin/delete',
        type:'post',
        dataType:'json',
        contentType:'application/json; charset=utf-8',
        data:JSON.stringify([id]),
        success:function (res) {
            console.log(res);
            if (res.code === 200){
                alert('删除成功');
                $('#productManage tbody').empty();
                listProduct();
            }else {
                alert('删除失败');
            }
        }
    });
}


//订单管理
function listCart() {
    $.ajax({
        url: '/cart/list',
        type: 'get',
        dataType: 'json',
        success: function(res) {
            console.log(res);

            // 清空表格的 tbody 部分
            $('#orderMange tbody').empty();

            // 遍历数据并插入新的行
            res.data.forEach(cart => {
                const row = `
	                <tr>
	                    <th scope="row"></th>
	                    <th scope="row">${cart.id}</th>
	                    <td>${cart.prodId}</td>
	                    <td>${cart.num}</td>
	                    <td>${cart.userId}</td>
	                    <td>${cart.prodName}</td>
	                    <td><img src="${cart.img}" alt="Product Image" style="max-width: 100px; height: auto;"></td>                  
	                    <td>${cart.price}</td>
	                    <td>
	                        <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#itemCartModal" data-bs-type="edit" data-bs-cart-id="${cart.id}">编辑</button>
	                        <button type="button" class="btn btn-sm btn-danger" onclick="delCart(${cart.id})">删除</button>
	                    </td>
	                </tr>`;
                $('#orderMange tbody').append(row);
            });
        },
        error: function(xhr, status, error) {
            // 可选：处理 AJAX 请求失败的情况
            console.error("AJAX Error: " + status + ", " + error);
            // 可以在这里添加一些错误处理的逻辑，比如显示错误消息给用户
        }
    });
}

function addCart() {
    const cart = {
        id: $('#cartId').val(),
        prodId: $('#prodId').val(),
        num: $('#prodNum').val(),
        userId: $('#userCartId').val(),
    };
    $.ajax({
        url: '/cart/admin/insert',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(cart),
        success: function (res) {
            console.log(res);
            if (res.code === 200) {
                alert('添加成功');
                $('#itemCartModal ').modal('hide');
                $('#orderMange tbody').empty();
                listCart();
            } else {
                alert('添加失败');
            }
        }
    });
}

function editCart(event){
    const id = event.data.id;
    const cart = {
        id: id,
        prodId: $('#prodId').val(),
        num: $('#prodNum').val(),
        userId: $('#userCartId').val(),
    };
    $.ajax({
        url:'/cart/admin/update',
        type:'post',
        dataType:'json',
        contentType: 'application/json; charset=utf-8',
        data:JSON.stringify(cart),
        success:function (res) {
            console.log(res);
            if (res.code === 200) {
                alert('修改成功');
                $('#itemCartModal').modal('hide');
                $('#orderManage tbody').empty();
                listCart();
            } else {
                alert('修改失败');
            }
        }
    });
}

function delCart(id) {
    $.ajax({
        url:'/cart/admin/delete',
        type:'post',
        dataType:'json',
        contentType:'application/json; charset=utf-8',
        data:JSON.stringify([id]),
        success:function (res) {
            console.log(res);
            if (res.code === 200){
                alert('删除成功');
                $('#orderMange tbody').empty();
                listCart();
            }else {
                alert('删除失败');
            }
        }
    });
}

function getCart(id) {
    $.ajax({
        url: '/cart/admin/'+ id,
        type: 'get',
        dataType: 'json',
        success:function (res) {
            console.log(res);
            //res.data渲染为modal初始值
            const cart = res.data;
            $('#cartId').val(cart.id);
            $('#prodId').val(cart.prodId);
            $('#prodNum').val(cart.prodNum);
            $('#userCartId').val(cart.userId);
        }
    });
}



//轮播图管理
function listImg() {
    $.ajax({
        url: '/banner/list',
        type: 'get',
        dataType: 'json',
        success: function(res) {
            console.log(res);

            // 清空表格的 tbody 部分
            $('#imgMange tbody').empty(); // 注意：这里应该是 goodsManage 而不是 goodsMange，确保选择器正确

            // 遍历数据并插入新的行
            res.data.forEach(imgL => {
                const row = `
	                <tr>
	                    <th scope="row">${imgL.id}</th>
	                    <td><img src="${imgL.img}" alt="Product Image" style="max-width: 100px; height: auto;"></td>
	                    <td>${imgL.url}</td>
	                    <td>${imgL.description}</td>
	                    <td>
	                        <button type="button" class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#itemImgModal" data-bs-type="edit" data-bs-img-id="${imgL.id}">编辑</button>
	                        <button type="button" class="btn btn-sm btn-danger" onclick="delImg(${imgL.id})">删除</button>
	                    </td>
	                </tr>`;
                $('#imgMange tbody').append(row);
            });
        },
        error: function(xhr, status, error) {
            // 可选：处理 AJAX 请求失败的情况
            console.error("AJAX Error: " + status + ", " + error);
            // 可以在这里添加一些错误处理的逻辑，比如显示错误消息给用户
        }
    });
}

function getImg(id) {
    $.ajax({
        url: '/banner/admin/'+ id,
        type: 'get',
        dataType: 'json',
        success:function (res) {
            console.log(res);
            //res.data渲染为modal初始值
            const imgL = res.data;
            $('#imgId').val(imgL.id);
            $('#imgLocal').val(imgL.img);
            $('#imgUrl').val(imgL.url);
            $('#imgDes').val(imgL.description);
        }
    });
}


function addImg() {
    const imgL = {
        id: $('#imgId').val(),
        img: $('#imgLocal').val(),
        url: $('#imgUrl').val(),
        des: $('#imgDes').val(),
    };
    $.ajax({
        url: '/banner/admin/insert',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(imgL),
        success: function (res) {
            console.log(res);
            if (res.code === 200) {
                alert('添加成功');
                $('#itemImgModal ').modal('hide');
                $('#imgMange tbody').empty();
                listImg();
            } else {
                alert('添加失败');
            }
        }
    });
}

function editImg(event){
    const id = event.data.id;
    const imgL = {
        id: id,
        img: $('#imgLocal').val(),
        url: $('#imgUrl').val(),
        des: $('#imgDes').val(),
    };
    $.ajax({
        url:'/banner/admin/update',
        type:'post',
        dataType:'json',
        contentType: 'application/json; charset=utf-8',
        data:JSON.stringify(imgL),
        success:function (res) {
            console.log(res);
            if (res.code === 200) {
                alert('修改成功');
                $('#itemImgModal').modal('hide');
                $('#imgMange tbody').empty();
                listImg();
            } else {
                alert('修改失败');
            }
        }
    });
}

function delImg(id) {
    $.ajax({
        url:'/banner/admin/delete',
        type:'post',
        dataType:'json',
        contentType:'application/json; charset=utf-8',
        data:JSON.stringify([id]),
        success:function (res) {
            console.log(res);
            if (res.code === 200){
                alert('删除成功');
                $('#imgMange tbody').empty();
                listImg();
            }else {
                alert('删除失败');
            }
        }
    });
}






//暂无作用
function listCategory() {
    $.ajax({
        url: '/category/list',
        type: 'get',
        dataType: 'json',
        success: function (res) {
            console.log(res);
            //res.data渲染为表格的行
            res.data.forEach(cat => {
                const option =
                    `<option value="${cat.id}">${cat.name}</option>`;
                $('#categoryId').append(option);
            })

        }
    });
}


function getLoginUser() {
    $.ajax({
        url: '/user/isLogin',
        type: 'get',
        dataType: 'json',
        success:function (res) {
            console.log(res);
            const user = res.data;
            $('#loginUser').text(user.nickName);
        }
    });
}

function logout() {
    $.ajax({
        url:'/user/logout',
        type:'get',
        dataType:'json',
        success:function (res) {
            console.log(res);
            if (res.code === 200){
                alert('退出成功');
                window.location.href = '/login.html'
            }else {
                alert('退出失败');
            }
        }
    });
}

//清空表单
function clearUserModal(){
    $('#userId').val('');
    $('#userPhone').val('');
    $('#nickName').val('');
}

function clearCategoryModal(){
    $('#cateId').val('');
    $('#cateCode').val('');
    $('#cateName').val('');
}

function clearModal(){
    $('#title').val('');
    $('#code').val('');
    $('#price').val('');
    $('#stocks').val('');
    $('#description').val('');
    $('#categoryId').val('');
    $('#img').val('');
    $('#imgPreview').attr('src','');
    $('#imgUpload').val('');
}

function clearCartModal(){
    $('#cartId').val('');
    $('#prodId').val('');
    $('#prodNum').val('');
    $('#userCartId').val('');
}

function clearImgModal(){
    $('#imgId').val('');
    $('#imgLocal').val('');
    $('#imgUrl').val('');
    $('#imgDes').val('');
}