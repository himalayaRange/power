/*
 * Copyright 2008-2018 blysy.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */
package com.bly.common.concurrent;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @since 1.7
 * 
 * JDK 未提供ConcurrentHashSet,可通过ConcurrentHashMap实现
 * 
 * Created by wangyi.
 */
public class ConcurrentHashSet<E> extends  AbstractSet<E> implements Set<E> , java.io.Serializable{

	private static final long serialVersionUID = -8672117787651310382L;  
	  
    private static final Object PRESENT = new Object();  
  
    private final ConcurrentHashMap<E, Object> map;  
      
    public ConcurrentHashSet(){  
        map = new ConcurrentHashMap<E, Object>();  
    }  
  
    public ConcurrentHashSet(int initialCapacity){  
        map = new ConcurrentHashMap<E, Object>(initialCapacity);  
    }  
    
    public Iterator<E> iterator() {  
        return map.keySet().iterator();  
    }  

    public int size() {  
        return map.size();  
    }  

    public boolean isEmpty() {  
        return map.isEmpty();  
    }  

    public boolean contains(Object o) {  
        return map.containsKey(o);  
    } 
 
    public boolean add(E e) {  
        return map.put(e, PRESENT) == null;  
    }  

    public boolean remove(Object o) {  
        return map.remove(o) == PRESENT;  
    }  

    public void clear() {  
        map.clear();  
    }  
}
