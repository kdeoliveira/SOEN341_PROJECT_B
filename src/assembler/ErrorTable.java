// package assembler;

// import java.util.AbstractMap;
// import java.util.Collection;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.Set;

// public class ErrorTable<S extends Comparable<S>, V, T> extends AbstractMap<T, Error<S, V, T>> {

//     private Map<T, Error<S, V, T>> errorList;
//     private T type;

//     public ErrorTable(T type) {
//         errorList = new HashMap<>();
//         this.type = type;
//     }

//     @Override
//     public Set<Entry<T, Error<S, V, T>>> entrySet() {
//         throw new UnsupportedOperationException();
//     }

//     @Override
//     public Set<T> keySet() {
//         return this.errorList.keySet();
//     }

//     @Override
//     public Collection<Error<S, V, T>> values() {
//         return this.errorList.values();
//     }

//     @Override
//     public void clear() {
//         this.errorList.clear();
//     }

//     @Override
//     public boolean containsKey(Object arg0) {
//         return this.errorList.containsKey(arg0);
//     }

//     @Override
//     public boolean containsValue(Object arg0) {
//         return this.errorList.containsValue(arg0);
//     }

//     @Override
//     public Error<S, V, T> get(Object arg0) {
//         return this.errorList.get(arg0);
//     }

//     @Override
//     public boolean isEmpty() {
//         return this.errorList.isEmpty();
//     }

//     @Override
//     public Error<S, V, T> put(T arg0, Error<S, V, T> arg1) {
//         return this.errorList.put(arg0, arg1);
//     }

//     @Override
//     public void putAll(Map<? extends T, ? extends Error<S, V, T>> arg0) {
//         this.errorList.putAll(arg0);
//     }

//     @Override
//     public Error<S, V, T> remove(Object arg0) {
//         return this.errorList.remove(arg0);
//     }

//     @Override
//     public boolean equals(Object arg0) {
//         return super.equals(arg0);
//     }

//     @Override
//     public int hashCode() {
//         return super.hashCode();
//     }

//     @Override
//     public int size() {
//         return this.errorList.size();
//     }
// }
