# GapoTest

## Bài 1
 Implement MVVM, ViewModel, LiveData, sử dụng Koin và Kotlin Coroutines 

## Bài 2
 *Cache:*
 + Sử dụng RoomSql để lưu data danh sách newsfeed xuống local sau khi fetch data
 + Sử dụng onSaveInstance để lưu trạng thái fragment khi chuyển tab dưới Bottom
 
 *Resource:*
 + Sử dụng vector xml thay cho các file ảnh
 + Sử dụng shrink resource để loại bỏ resource không cần thiết trong các thư viện
 
 ## Bài 3
*Trường hợp newsfeed phức tạp, sẽ xử lý theo từng loại content:*
- hashtag: kiểm tra content có chứa #string , nếu có highlight và set click bằng spandable text
- link: kiểm tra content có chứa https://link , xử lý tương tự hashtag
- album ảnh: tạo 1 trường album khi load ảnh vào album
- mention: đặt đoạn json chứa thông tin user được mention và xử lý tương tự hashtag

* Với trường hợp sau n bài viết sẽ hiển thị slide ngang, kiểm tra position trong list nếu chia hết cho n sẽ set lại viewType là slide
 
