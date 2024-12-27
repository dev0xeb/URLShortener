import { useState } from 'react'
import Header from './components/Headers'
import MainSection from './components/MainSection'
import './index.css'

function App() {

  return (
    <>
      <div className='app'>
        <Header />
        <MainSection />
      </div>
    </>
  )
}

export default App
