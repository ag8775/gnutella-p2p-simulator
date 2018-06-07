package p2psimulator.element;

/**
 * <p>Title: Peer to Peer Simulator</p>
 * <p>Description: Self Organization Protocols for Peer to Peer Systems</p>
 * <p>Copyright: Copyright (c) 2003 </p>
 * <p>Company: Wayne State University (Networking Wireless Sensors Lab)</p>
 * @author Manish M Kochhal
 * @version 1.0
 */

import java.util.*;

class QueueNode {
   Object item;
   QueueNode link ;

  }


public class Queue
{

    private QueueNode front;

    private QueueNode rear;

    private int count = 0;


    public Queue() {};

   public boolean isEmpty()
    {
      return (count == 0);
     }

   public int length()
    {
      return count;
    }

   public void enqueue(Object newItem)
     {

       QueueNode temp = new QueueNode();

       temp.item = newItem;

       temp.link = null ;

       if (rear == null)
         {
            front = rear =  temp ;
          }
       else
         {
           rear.link = temp ;
           rear = temp;
         }
         count++;
      } //end Insert

      public Object dequeue()
        {
          if (count==0)
            return null;
          else
            {
               Object tempItem = front.item;
               front = front.link;
                if (front == null)
                  {
                    rear = null;
                  }
                  count--;

                  return tempItem;
             }
         }

      public Object checkFront()
       {
          if(count == 0)
            return null;
          else
           return front.item;
       }

}
