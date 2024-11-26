import React, { useState } from 'react';

const ProductForm = () => {
    const [productName, setProductName] = useState('');
    const [barcode, setBarcode] = useState('');
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState('available');
    const [colorList, setColorList] = useState([]);
    const [sizeList, setSizeList] = useState([]);
    const [variants, setVariants] = useState([]);
    const [newColor, setNewColor] = useState('');
    const [newSize, setNewSize] = useState('');
    const [quantity, setQuantity] = useState('');
    const [price, setPrice] = useState('');

    const handleAddVariant = () => {
        if (!newColor || !newSize || !quantity || !price) {
            alert('Vui lòng điền đầy đủ thông tin biến thể!');
            return;
        }
        setVariants([...variants, { color: newColor, size: newSize, quantity, price }]);
        setNewColor('');
        setNewSize('');
        setQuantity('');
        setPrice('');
    };

    const handleAddColor = () => {
        if (!newColor) return;
        setColorList([...colorList, newColor]);
        setNewColor('');
    };

    const handleAddSize = () => {
        if (!newSize) return;
        setSizeList([...sizeList, newSize]);
        setNewSize('');
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Xử lý gửi dữ liệu sản phẩm
        console.log({
            productName,
            barcode,
            description,
            status,
            variants,
        });
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center mb-4">Thêm Sản Phẩm</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="productName">Tên Sản Phẩm</label>
                    <input type="text" className="form-control" id="productName" value={productName}
                        onChange={(e) => setProductName(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="barcode">Mã vạch</label>
                    <input type="number" className="form-control" id="barcode" value={barcode}
                        onChange={(e) => setBarcode(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="description">Mô Tả</label>
                    <textarea className="form-control" id="description" rows="3" value={description}
                        onChange={(e) => setDescription(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="status">Trạng Thái</label>
                    <select className="form-control" id="status" value={status}
                        onChange={(e) => setStatus(e.target.value)}>
                        <option value="available">Còn hàng</option>
                        <option value="out_of_stock">Hết hàng</option>
                        <option value="coming_soon">Sắp có hàng</option>
                    </select>
                </div>

                <h5 className="mt-4">Thêm Biến Thể</h5>
                <div className="form-group">
                    <label htmlFor="color">Màu Sắc</label>
                    <input type="text" className="form-control" value={newColor}
                        onChange={(e) => setNewColor(e.target.value)} placeholder="Nhập màu sắc" />
                    <button type="button" className="btn btn-primary mt-2" onClick={handleAddColor}>Thêm Màu</button>
                </div>
                <div className="form-group">
                    <label htmlFor="size">Kích Cỡ</label>
                    <input type="text" className="form-control" value={newSize}
                        onChange={(e) => setNewSize(e.target.value)} placeholder="Nhập kích cỡ" />
                    <button type="button" className="btn btn-primary mt-2" onClick={handleAddSize}>Thêm Kích Cỡ</button>
                </div>
                <div className="form-group">
                    <label htmlFor="quantity">Số Lượng</label>
                    <input type="number" className="form-control" value={quantity}
                        onChange={(e) => setQuantity(e.target.value)} placeholder="Nhập số lượng" />
                </div>
                <div className="form-group">
                    <label htmlFor="price">Giá</label>
                    <input type="number" className="form-control" value={price}
                        onChange={(e) => setPrice(e.target.value)} placeholder="Nhập giá" />
                </div>
                <button type="button" className="btn btn-success mt-2" onClick={handleAddVariant}>Thêm Biến Thể</button>

                <h5 className="mt-4">Biến Thể Sản Phẩm</h5>
                {variants.map((variant, index) => (
                    <div key={index} className="border p-2 mb-2">
                        <p><strong>Màu:</strong> {variant.color}</p>
                        <p><strong>Kích Cỡ:</strong> {variant.size}</p>
                        <p><strong>Số Lượng:</strong> {variant.quantity}</p>
                        <p><strong>Giá:</strong> {variant.price} VND</p>
                    </div>
                ))}

                <button type="submit" className="btn btn-primary mt-3">Thêm Sản Phẩm</button>
            </form>

            <div className="mt-4">
                <h5>Màu Sắc đã thêm:</h5>
                <ul>
                    {colorList.map((color, index) => <li key={index}>{color}</li>)}
                </ul>
                <h5>Kích Cỡ đã thêm:</h5>
                <ul>
                    {sizeList.map((size, index) => <li key={index}>{size}</li>)}
                </ul>
            </div>
        </div>
    );
};

export default ProductForm;
