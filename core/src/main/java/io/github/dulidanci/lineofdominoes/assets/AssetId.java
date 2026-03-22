package io.github.dulidanci.lineofdominoes.assets;

public interface AssetId<T> {
    String path();
    Class<T> type();
}
