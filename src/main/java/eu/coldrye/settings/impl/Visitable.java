package eu.coldrye.settings.impl;

public interface Visitable<T> {

  void accept(T visitor);
}
