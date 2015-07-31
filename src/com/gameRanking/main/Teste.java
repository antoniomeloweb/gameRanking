package com.gameRanking.main;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Teste {
	public static void main(String[] args) {
		String[] used={"AK47","AK47","MP5","MP5","DESERT EAGLE","KNIFE","BAZOOKA","DESERT EAGLE","GRENADE","KNIFE","KNIFE"};
		
		TreeMap<String, Integer> weaponUseage = new TreeMap<String, Integer>();
		
		for(String str: used){
			if(!weaponUseage.containsKey(str)){
				weaponUseage.put(str, 1);
			}else{
				weaponUseage.put(str, weaponUseage.get(str).intValue()+1);
			}
		}
		
		ValueComparator bvc =  new ValueComparator(weaponUseage);
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
		sorted_map.putAll(weaponUseage);
		
		System.out.println(weaponUseage);
		System.out.println(sorted_map);

	}
	
}

class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;
    public ValueComparator(TreeMap<String, Integer> base) {
        this.base = base;
    }
    
    public int compare(String a, String b) {
    	//doing desc order
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}