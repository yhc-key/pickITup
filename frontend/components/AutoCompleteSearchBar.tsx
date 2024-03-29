"use client"
import { useState,useEffect } from "react";
import { techMap2 } from "@/data/techData";
class TrieNode {
  children : {[key: string]: TrieNode};
  isEndOfWord : boolean;
  constructor() {
    this.children = {};
    this.isEndOfWord = false;
  }
}

class Trie {
  root : TrieNode;
  constructor() {
    this.root = new TrieNode();
  }

  insert(word:string) {
    let currentNode :TrieNode= this.root;
    for (let i = 0; i < word.length; i++) {
      const char:string = word[i];
      if (!currentNode.children[char]) {
        currentNode.children[char] = new TrieNode();
      }
      currentNode = currentNode.children[char];
    }
    currentNode.isEndOfWord = true;
  }

  search(word:string) {
    let currentNode :TrieNode = this.root;
    for (let i = 0; i < word.length; i++) {
      const char:string = word[i];
      if (!currentNode.children[char.toUpperCase()]&&!currentNode.children[char.toLowerCase()]) {
        return false;
      }
    
        currentNode = currentNode.children[char];
      
    }
    return currentNode.isEndOfWord;
  }

  startsWith(prefix:string) {
    let currentNode:TrieNode = this.root;
    for (let i = 0; i < prefix.length; i++) {
      const char:string = prefix[i];
      if (!currentNode.children[char]) {
        return false;
      }
      currentNode = currentNode.children[char];
    }
    return true;
  }

  getAllWordsWithPrefix(prefix:string) {
    const result:string[] = [];
    let currentNode:TrieNode = this.root;
    for (let i = 0; i < prefix.length; i++) {
      const char:string = prefix[i];
      if (!currentNode.children[char]) {
        return result;
      }
      currentNode = currentNode.children[char];
    }
    this._getAllWordsFromNode(currentNode, prefix, result);
    return result;
  }

  _getAllWordsFromNode(node:TrieNode, prefix:string, result:string[]) {
    if (node.isEndOfWord) {
      result.push(prefix);
    }
    for (const childChar in node.children) {
      this._getAllWordsFromNode(
        node.children[childChar],
        prefix + childChar,
        result
      );
    }
  }
}

interface AutocompleteSearchBarProps {
  words: string[];
  onSelect: (tech: string) => void;
}
function AutocompleteSearchBar(props:AutocompleteSearchBarProps) {
  const [inputValue, setInputValue] = useState<string>("");
  const [suggestions, setSuggestions] = useState<string[]>([]);
  const [selectedTechs, setSelectedTechs] = useState<string[]>([]); 
  
  const trie = new Trie();
  props.words.forEach((word) => trie.insert(word.toLowerCase())); //소문자

  const handleInputChange = (event:React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value.toLowerCase();//소문자
    setInputValue(value);
    if (value === "") {
      setSuggestions([]);
    } else {
      const suggestions = trie.getAllWordsWithPrefix(value.toLowerCase());
      setSuggestions(suggestions);
    }
  };

  const handleSuggestionClick = (suggestion:string) => {
    const origin :string|undefined = techMap2.get(suggestion);
    if(origin){
      props.onSelect(origin);
      setInputValue("");
      setSelectedTechs(prevState => [...prevState, suggestion]);
    }
    setSuggestions([]);
  };
  
  return (
    <div className="relative w-[20vw]">
      <input
        type="text"
        value={inputValue}
        onChange={handleInputChange}
        onFocus={() => {
          if (inputValue.trim() !== "") {
            const suggestions = trie.getAllWordsWithPrefix(inputValue.toLowerCase());
            setSuggestions(suggestions);
          }
        }}
        placeholder="아래에 없는 기술은 검색해보세요!"
        className="w-full h-8 rounded-md border border-f5gray-400 bg-gray-200 px-2 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-f5green-300"
      />
      {suggestions.length > 0 && (
        <ul className="absolute z-10 top-[calc(100% + 5px)] w-[20vw] max-h-[130px] overflow-y-auto bg-white border border-f5gray-400 rounded-b-md">
          {suggestions.map((suggestion) => (
            <li
              key={suggestion}
              onClick={() => handleSuggestionClick(suggestion)}
              className="px-2 py-1 cursor-pointer
              hover:outline-none 
            hover:border-f5green-300 hover:bg-green-100"
            >
              {suggestion}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
export default AutocompleteSearchBar;
