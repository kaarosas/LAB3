package laborai.studijosktu;

import lab3Baranauskas.Vaisius;

public class MapKTUOA implements MapADT<String, Vaisius>
{
    class hashEntry
    {
        String key;
        Vaisius value;

        public hashEntry(String key, Vaisius value)
        {
            this.key = key;
            this.value = value;
        }           
        
        @Override
        public String toString()
        {
            return key + "=" + value;
        }
    }
   
    private hashEntry[] hashArray;
    protected int size = 0;
    protected HashType ht;
    private int prime;
    private int Capacity;
       
    public MapKTUOA(int capacity)
    {
        Capacity = capacity;
        prime = getPrime();
        size = 0;
        hashArray = new hashEntry[Capacity];
    }
    
    
    @Override 
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public int size()
    {
        return size;
    }
    
    public int getPrime()
    {
        for (int i = Capacity - 1; i >= 1; i--)
        {
            int fact = 0;
            for (int j = 2; j <= (int) Math.sqrt(i); j++)
            {
                if (i % j == 0)
                {
                    fact++;
                }
            }
            if (fact == 0)
            {
                return i;
            }
        }
        return 3;
    }
    
    public int hashFunc1(String key)
    {
        int hasVal = key.hashCode();
        hasVal %= Capacity;
        if (hasVal < 0)
        {
            hasVal += Capacity;
        }
        return hasVal;
    }
    
    public int hashFunc2(String key)
    {
        int hashVal = key.hashCode();
        hashVal %= Capacity;
        if (hashVal < 0)
        {
            hashVal += Capacity;
        }
        return prime - hashVal % prime;
    }
    
    @Override
    public void clear()
    {
        size = 0;
        for (int i = 0; i < Capacity; i++)
        {
            hashArray[i] = null;
        }
    }

    @Override
    public String[][] toArray()
    {
        String[][] result = new String[Capacity][];
        int count = 0;
        for (hashEntry a : hashArray)
        {
            String[] list = new String[size+1];
            int countLocal = 0;
            while (a != null)
            {                
                list[countLocal++] = a.toString(); 
                break;
            }
            result[count] = list;
            count++;
        }
        return result;
    }

    @Override
    public Vaisius put(String key, Vaisius value)
    {
        Vaisius replaced;
        if (size == Capacity)
        {
            rehash();            
        }
        int hash1 = hashFunc1(key);
        int hash2 = hashFunc2(key);
        int hash = 0;
        
        for (int i = 0; i < Capacity; i++)
        {
            hash = (hash1 + (i*hash2)) % prime;
            while (hashArray[hash] != null)
            {                
                break;
            }
            break;
        }
        if (hashArray[hash] == null)
        {
            replaced = null;
        }        
        else
        {
            replaced = hashArray[hash].value;
        }
        hashArray[hash] = new hashEntry(key, value);
        size++;
        return replaced;
    }

    private void rehash()
    {
        Capacity = Capacity * 2;
        MapKTUOA map = new MapKTUOA(Capacity);       
        for (int i = 0; i < hashArray.length; i++)
        {
            while (hashArray[i] != null)
            {                
                map.put(hashArray[i].key, hashArray[i].value);
                break;
            }
        }
        hashArray = map.hashArray;
    }

    @Override
    public Vaisius get(String key)
    {
        int hash1 = hashFunc1(key);
        int hash2 = hashFunc2(key);
        int hash = 0;
        for (int i = 0; i < 10; i++)
        {
            hash = (hash1 + (i*hash2)) % prime;
            while (hashArray[hash] != null && !hashArray[hash].key.equals(key))
            {            
                break;
            }
            break;
        }
        return hashArray[hash1].value;
    }

    @Override
    public Vaisius remove(String key)
    {
        Vaisius rem;
        int hash1 = hashFunc1(key);
        int hash2 = hashFunc2(key);
        int hash = 0;
        for (int i = 0; i < Capacity; i++)
        {
            hash = (hash1 + (i*hash2)) % prime;
            while (hashArray[hash] != null && !hashArray[hash].key.equals(key))
            {            
                break;
            }
            break;
        }
        if (hashArray[hash1] == null)
        {
            rem = null;
        }
        else
        {
            rem = hashArray[hash1].value;
        }
        hashArray[hash1] = null;
        size--;
        return rem;
    }

    @Override
    public boolean contains(String key)
    {
        for (hashEntry a : hashArray)
        {
            while (a != null)
            {         
                if (a.key.equals(key))
                {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (hashEntry a : hashArray) {
            if (a != null) {
                for (int i = 0; i < 1; i++)
                {
                    result.append(a.toString()).append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    } 
}
