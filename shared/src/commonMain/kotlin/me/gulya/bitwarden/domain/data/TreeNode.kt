package me.gulya.bitwarden.domain.data

data class TreeNode<T : ITreeNodeObject?>(
    val node: T,
    val name: String?,
    val parent: T?,
    val children: List<TreeNode<T>>,
)