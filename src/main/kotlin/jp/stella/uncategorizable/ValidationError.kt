package jp.stella.uncategorizable

/**
 * ドメインオブジェクトのバリデーションにおけるエラー型
 *
 * 必ずエラーメッセージを記述する
 */

interface ValidationError{
    /**
     * エラーメッセージ
     */
    val errorMessage: String
}

// TODO: ドメインオブジェクトをinterfaceを利用した実装に修正したら削除
//data class ValidationError(val errorMessage: String)