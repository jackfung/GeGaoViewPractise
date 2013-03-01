1:ExpandableListView中child是否可以点击，是由该回调函数决定的：

 @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    
    
    
2:ExpandableListView中child view中有进度条，但是进度条就是不能及时更新，只有在
展开或这关闭父view的时候，进度条才会变化，是因为覆盖了不应该覆盖的方法，这些不应该
覆盖的方法是：

  @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }
    
  
  去掉这些覆盖方法，即可保证在父view 展开（及子view可以看到的情况）下，进度条可以自动更新。