package jp.stella.entity.store



/**
 * Web API で利用するデータモデル
 *
 * Repository によるDB とのやりとり、Serviceを介したデータの受け渡し、Controller のレスポンスに利用する
 *
 * @property storeId ストアID
 * @property storeName 店名
 * @property address 住所
 */
data class Store(val storeId: String, val storeName: String,val address: String) {

}

