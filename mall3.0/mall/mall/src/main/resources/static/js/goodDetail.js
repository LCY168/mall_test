$(document).ready(function() {
    // 从URL的查询字符串中获取id参数
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id'); // 假设URL是这样的：http://example.com/?id=123

    if (id) {
        getProduct(id); // 如果有id，则调用getProduct函数
    } else {
        console.error('No id parameter found in the URL.');
        // 这里可以添加处理没有id参数的情况的代码
    }


});

function saveAndGoCart() {
    const queryString = window.location.search; // 获取查询字符串，例如 "?id=123"
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('id'); // 提取 id 的值

    if (!id) {
        alert('ID 参数未找到！');
        return;
    }

    const userId = sessionStorage.getItem("userId"); // 从 sessionStorage 获取 userId
    if (!userId) {
        alert('用户未登录或登录.已过期！');
        return;
    }
    const cart = {
        prodId: id,
        num: 1,
        userId: userId,
    };
    $.ajax({
        type: 'POST',
        url: '/cart/admin/insert',
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(cart),

        success: function (result) {
            console.log(cart);
            if (result.code == 200) {
                alert("添加成功")
                window.location.href = '/shopcart.html';
            }
            else {
                alert("添加失败")
            }
        }

    });
}




function getProduct(id) {
    if (!id) {
        console.error('没有id');
        return; // 如果没有有效的id，则直接返回，不执行后续代码
    }

    $.ajax({
        url: '/product/admin/' + id,
        type: 'get',
        dataType: 'json',
        success: function(res) {
            console.log(res);
            // 假设res.data包含了产品的信息
            if (res && res.data) {
                const prod = res.data;
                let s1 = "";
                s1+=`
                <div class="mr-pic">
                    <dl>
                        <dt><img src="${prod.img}" alt="商品图片" style="height: 410px; width: 320px"></dt>
                    </dl>
                </div>
                `;
                s1 +=`
                    <div class="mr-mess">
                            <br>
                        <br>
                        <div>
                            <h3>${prod.title}</h3>
                        </div>
                        <div class="mr-price">
                            <font size="-1" color="#FF0099">价格&nbsp;&nbsp;&nbsp;￥<font size="+2">${prod.price}+</font>
                            </font>
                        </div><br>
                        <hr>
                        <br>
                        <div class="dul">
                            <ul>
                                <li class="list1">
                                    <font color="#CCCCCC">商品库存&nbsp;</font>
                                </li>
                                <li>${prod.stocks}</li>
                            </ul>

                            <br><br><br>
                            <ul>
                                <li class="list1">
                                    <font color="#CCCCCC">商品描述&nbsp;</font>
                                </li>
                                <li>${prod.description}</li>
                            </ul>
                            <br><br><br>
                            <button type="button" class="btn btn-primary" id="addbutton" onclick="saveAndGoCart()">加入购物车</button>
                        </div>
                    </div>`;

                $('#getProd').append(s1);
            } else {
                console.error('Invalid response from server.');
            }
        },
        error: function(xhr, status, error) {
            // 处理请求失败的情况
            console.error("Error fetching product data: " + error);
        }
    });
}